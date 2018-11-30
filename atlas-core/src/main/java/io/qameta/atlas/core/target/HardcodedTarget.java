package io.qameta.atlas.core.target;

import io.qameta.atlas.core.api.Target;

/**
 * @author Artem Eroshenko.
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class HardcodedTarget implements Target {

    private final String name;

    private final Object instance;

    public HardcodedTarget(final String name, final Object instance) {
        this.instance = instance;
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object instance() {
        return instance;
    }

}
