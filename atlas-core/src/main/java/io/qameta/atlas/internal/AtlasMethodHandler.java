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

    private final Configuration configuration;

    private final ListenerNotifier notifier;

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
            final Object result = handler.invoke(proxy, methodInfo, configuration);
            notifier.onMethodReturn(methodInfo, configuration, result);
            return result;
        } catch (Throwable e) {
            notifier.onMethodFailure(methodInfo, configuration, e);
            throw e;
        } finally {
            notifier.afterMethodCall(methodInfo, configuration);
        }
    }

}
