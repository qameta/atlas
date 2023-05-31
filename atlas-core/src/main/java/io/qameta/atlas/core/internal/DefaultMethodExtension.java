package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.util.MethodInfo;
import org.apache.commons.lang3.JavaVersion;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.apache.commons.lang3.SystemUtils.isJavaVersionAtMost;

/**
 * Default method extension.
 */
public class DefaultMethodExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isDefault();
    }

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration config) throws Throwable {
        final Class<?> declaringClass = methodInfo.getMethod().getDeclaringClass();

        if (isJavaVersionAtMost(JavaVersion.JAVA_1_8)) {
            final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                    .getDeclaredConstructor(Class.class, int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
                    .unreflectSpecial(methodInfo.getMethod(), declaringClass)
                    .bindTo(proxy)
                    .invokeWithArguments(methodInfo.getArgs());
        }

        final MethodHandle methodHandle = MethodHandles.lookup().findSpecial(
                declaringClass,
                methodInfo.getMethod().getName(),
                MethodType.methodType(
                        methodInfo.getMethod().getReturnType(),
                        methodInfo.getMethod().getParameterTypes()
                ),
                declaringClass
        );

        return methodHandle
                .bindTo(proxy)
                .invokeWithArguments(methodInfo.getArgs());
    }
}
