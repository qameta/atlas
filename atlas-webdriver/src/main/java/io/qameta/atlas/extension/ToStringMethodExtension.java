package io.qameta.atlas.extension;

import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;

import java.lang.reflect.Method;

/**
 * ToString method extension.
 */
public class ToStringMethodExtension implements MethodExtension {

    private static final String TO_STRING = "toString";

    private final String name;


    public ToStringMethodExtension(final String name) {
        this.name = name;
    }

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(TO_STRING);
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        return name;
    }

}
