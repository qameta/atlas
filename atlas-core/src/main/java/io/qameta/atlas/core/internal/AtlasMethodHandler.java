package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.core.api.MethodInvoker;
import io.qameta.atlas.core.api.Retry;
import io.qameta.atlas.core.api.Timeout;
import io.qameta.atlas.core.util.MethodInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Atlas method handler.
 */
public class AtlasMethodHandler implements InvocationHandler {

    private final ListenerNotifier notifier;

    private final Configuration configuration;

    private final Map<Method, MethodInvoker> handlers;

    public AtlasMethodHandler(final Configuration configuration,
                              final ListenerNotifier listenerNotifier,
                              final Map<Method, MethodInvoker> handlers) {
        this.configuration = configuration;
        this.notifier = listenerNotifier;
        this.handlers = handlers;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final MethodInfo methodInfo = new MethodInfo(method, args);

        notifier.beforeMethodCall(methodInfo, configuration);
        try {
            final MethodInvoker handler = handlers.get(method);
            final Object result = invokeWithRetry(handler, proxy, methodInfo);
            notifier.onMethodReturn(methodInfo, configuration, result);
            return result;
        } catch (Throwable e) {
            notifier.onMethodFailure(methodInfo, configuration, e);
            throw e;
        } finally {
            notifier.afterMethodCall(methodInfo, configuration);
        }
    }

    private Object invokeWithRetry(final MethodInvoker invoker,
                                   final Object proxy,
                                   final MethodInfo methodInfo) throws Throwable {
        final DefaultRetryer retryer = Optional.ofNullable(methodInfo.getMethod().getAnnotation(Retry.class))
                .map(retry -> new DefaultRetryer(retry.timeout(), retry.polling(), Arrays.asList(retry.ignoring())))
                .orElseGet(() -> configuration.getContext(DefaultRetryer.class)
                        .orElseThrow(() -> new AtlasException("Can not find default retry settings")));
        retryer.ignore(Throwable.class);
        methodInfo.getParameter(Integer.class, Timeout.class).ifPresent(retryer::timeoutInSeconds);

        Throwable lastException;
        do {
            try {
                return invoker.invoke(proxy, methodInfo, configuration);
            } catch (Throwable e) {
                lastException = e;
            }
        } while (retryer.shouldRetry(lastException));
        throw lastException;
    }

}
