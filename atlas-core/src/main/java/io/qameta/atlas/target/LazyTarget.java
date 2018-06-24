package io.qameta.atlas.target;

import io.qameta.atlas.api.Target;

import java.util.function.Supplier;

/**
 * @author Artem Eroshenko.
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class LazyTarget implements Target {

    private final String name;

    private final Supplier<Object> supplier;

    public LazyTarget(final String name, final Supplier<Object> supplier) {
        this.supplier = supplier;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object instance() {
        return supplier.get();
    }
}
