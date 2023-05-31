package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.util.MethodInfo;
import org.apache.commons.lang3.SystemUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
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
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration config) throws Throwable {
        final Class<?> declaringClass = methodInfo.getMethod().getDeclaringClass();

        if (isJava8()) {
            final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                    .getDeclaredConstructor(Class.class, int.class);
            constructor.setAccessible(true);
            return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
                    .unreflectSpecial(methodInfo.getMethod(), declaringClass)
                    .bindTo(proxy)
                    .invokeWithArguments(methodInfo.getArgs());
        }

        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodHandle methodHandle = lookup.findSpecial(
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

    private boolean isJava8() {
        String[] versionElements = SystemUtils.JAVA_SPECIFICATION_VERSION.split("\\.");
        int discard = Integer.parseInt(versionElements[0]);
        int version;
        if (discard == 1) {
            version = Integer.parseInt(versionElements[1]);
        } else {
            version = discard;
        }

        return version == 8;
    }
}
