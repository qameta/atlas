package io.qameta.atlas;

import io.qameta.atlas.api.Extension;
import io.qameta.atlas.api.Listener;
import io.qameta.atlas.internal.AtlasMethodHandler;
import io.qameta.atlas.internal.TargetMethodInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.qameta.atlas.util.ReflectionUtils.getMethods;

/**
 * @author Artem Eroshenko.
 */
public class Atlas {

    private final List<Extension> extensions;

    private final List<Listener> listeners;

    public Atlas() {
        this.listeners = new ArrayList<>();
        this.extensions = new ArrayList<>();
    }

    public Atlas listener(final Listener listener) {
        this.listeners.add(listener);
        return this;
    }

    public Atlas extension(final Extension extension) {
        this.extensions.add(extension);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Object target, final Class<T> type) {
        final Map<Method, InvocationHandler> invokers = new HashMap<>();
        final List<Method> methods = getMethods(type, Object.class);

        methods.forEach(method -> invokers.put(method, new TargetMethodInvoker(() -> target)));

        extensions.forEach(extension -> {
            methods.stream().filter(extension).forEach(method -> invokers.put(method, extension));
        });

        return (T) Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[]{type},
                new AtlasMethodHandler(listeners, invokers)
        );
    }

}
