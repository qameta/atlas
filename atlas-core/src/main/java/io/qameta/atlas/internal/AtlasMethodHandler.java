package io.qameta.atlas.internal;

import io.qameta.atlas.api.MethodInvoker;
import io.qameta.atlas.util.MethodInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

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

    public Object invokeWithRetry(final MethodInvoker invoker,
                                  final Object proxy,
                                  final MethodInfo methodInfo) throws Throwable {
        final DefaultRetryer retryer = new DefaultRetryer();
        retryer.ignore(Throwable.class);

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
