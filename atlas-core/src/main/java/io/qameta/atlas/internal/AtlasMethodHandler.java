package io.qameta.atlas.internal;

import io.qameta.atlas.api.Listener;
import io.qameta.atlas.api.MethodInvoker;
import io.qameta.atlas.util.MethodInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Atlas method handler.
 */
public class AtlasMethodHandler implements InvocationHandler {

    private final Configuration config;

    private final ListenerNotifier notifier;

    private final Map<Method, MethodInvoker> handlers;

    public AtlasMethodHandler(final Configuration config,
                              final List<Listener> listeners,
                              final Map<Method, MethodInvoker> handlers) {
        this.notifier = new ListenerNotifier();
        this.handlers = handlers;
        this.config = config;

        listeners.forEach(notifier::addListeners);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final MethodInfo methodInfo = new MethodInfo(method, args);

        notifier.beforeMethodCall(methodInfo);
        try {
            final MethodInvoker handler = handlers.get(method);
            final Object result = handler.invoke(proxy, methodInfo, config);
            notifier.onMethodReturn(methodInfo, result);
            return result;
        } catch (Throwable e) {
            notifier.onMethodFailure(methodInfo, e);
            throw e;
        } finally {
            notifier.afterMethodCall(methodInfo);
        }
    }

}
