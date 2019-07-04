package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.api.MethodInvoker;
import io.qameta.atlas.core.api.Timeout;
import io.qameta.atlas.core.context.RetryerContext;
import io.qameta.atlas.core.util.MethodInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Consumer;

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
        final Configuration runConfig = notifier.beforeMethodCall(methodInfo, this.configuration);
        try {
            final MethodInvoker handler = handlers.get(method);
            final Object result = invokeWithRetry(handler, proxy, methodInfo);
            notifier.onMethodReturn(methodInfo, runConfig, result);
            return result;
        } catch (Throwable e) {
            notifier.onMethodFailure(methodInfo, runConfig, e);
            throw e;
        } finally {
            notifier.afterMethodCall(methodInfo, runConfig);
        }
    }

    private Object invokeWithRetry(final MethodInvoker invoker,
                                   final Object proxy,
                                   final MethodInfo methodInfo) throws Throwable {
        final Retryer retryer = getRetryer(configuration, methodInfo);
        Throwable lastException;
        do {
            try {
                return invoker.invoke(proxy, methodInfo, configuration);
            } catch (Throwable e) {
                lastException = e;
            }
        } while (retryer.shouldRetry(lastException, methodInfo));
        throw lastException;
    }

    private Retryer getRetryer(final Configuration configuration, final MethodInfo methodInfo) {
        final Retryer retryer = configuration.requireContext(RetryerContext.class).getValue();
        if (retryer instanceof TimeBasedRetryer) {
            final Consumer<Integer> time = ((TimeBasedRetryer) retryer)::setTimeOutInSeconds;
            methodInfo.getParameter(Integer.class, Timeout.class).ifPresent(time);
        }
        return retryer;
    }
}
