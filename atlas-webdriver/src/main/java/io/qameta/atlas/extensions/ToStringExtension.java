package io.qameta.atlas.extensions;

import io.qameta.atlas.api.Extension;
import java.lang.reflect.Method;

/**
 * ToString extension.
 */
public class ToStringExtension implements Extension {

    private static final String TO_STRING = "toString";

    private final String name;


    public ToStringExtension(final String name) {
        this.name = name;
    }

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(TO_STRING);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) {
        return name;
    }

}
