package io.qameta.atlas.internal;

import io.qameta.atlas.api.MethodExtension;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Default method extension.
 */
public class DefaultMethodExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isDefault();
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final Class<?> declaringClass = method.getDeclaringClass();
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class, int.class);
        constructor.setAccessible(true);
        return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
                .unreflectSpecial(method, declaringClass)
                .bindTo(proxy)
                .invokeWithArguments(args);
    }
}
