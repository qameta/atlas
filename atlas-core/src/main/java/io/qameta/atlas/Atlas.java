package io.qameta.atlas;

import io.qameta.atlas.internal.AtlasMethodHandler;
import io.qameta.atlas.internal.DefaultMethodInvoker;
import io.qameta.atlas.internal.TargetMethodInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static io.qameta.atlas.util.ReflectionUtils.getMethods;

/**
 * @author Artem Eroshenko.
 */
public class Atlas {

    @SuppressWarnings("unchecked")
    public <T> T create(final Object target, final Class<T> type) {
        final Map<Method, InvocationHandler> invokers = new HashMap<>();
        getMethods(type).forEach(method -> {
            invokers.put(method, new TargetMethodInvoker(() -> target));
            if (method.isDefault()) {
                invokers.put(method, new DefaultMethodInvoker());
            }
        });
        return (T) Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[]{type},
                new AtlasMethodHandler(invokers)
        );
    }

}
