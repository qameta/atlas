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

    private final Map<Method, MethodInvoker> handlers;

    private final ListenerNotifier notifier;

    public AtlasMethodHandler(final List<Listener> listeners, final Map<Method, MethodInvoker> handlers) {
        this.notifier = new ListenerNotifier();
        this.handlers = handlers;

        listeners.forEach(notifier::addListeners);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final MethodInfo methodInfo = new MethodInfo(method, args);

        notifier.beforeMethodCall(method, args);
        try {
            final MethodInvoker handler = handlers.get(method);
            final Object result = handler.invoke(proxy, methodInfo);
            notifier.onMethodReturn(method, args, result);
            return result;
        } catch (Throwable e) {
            notifier.onMethodFailure(method, args, e);
            throw e;
        } finally {
            notifier.afterMethodCall(method, args);
        }
    }

}
