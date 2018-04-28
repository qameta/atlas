package io.qameta.atlas;

import io.qameta.atlas.internal.AtlasMethodHandler;
import io.qameta.atlas.internal.TargetMethodInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static io.qameta.atlas.util.ReflectionUtils.getMethods;

/**
 * @author Artem Eroshenko.
 */
public class Atlas {

    private final Map<Predicate<Method>, InvocationHandler> extensions;

    private final List<Listener> listeners;

    public Atlas() {
        this.listeners = new ArrayList<>();
        this.extensions = new HashMap<>();
    }

    public Atlas listener(final Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    public Atlas extension(final Predicate<Method> predicate, final InvocationHandler handler) {
        this.extensions.put(predicate, handler);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Object target, final Class<T> type) {
        final Map<Method, InvocationHandler> invokers = new HashMap<>();
        getMethods(type, Object.class).forEach(method -> invokers.put(method, new TargetMethodInvoker(() -> target)));
        extensions.forEach((predicate, handler) ->
                getMethods(type).stream().filter(predicate).forEach(method -> invokers.put(method, handler)));

        return (T) Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[]{type},
                new AtlasMethodHandler(listeners, invokers)
        );
    }

}
