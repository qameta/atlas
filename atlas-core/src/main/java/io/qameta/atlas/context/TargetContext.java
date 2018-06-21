package io.qameta.atlas.context;

import io.qameta.atlas.api.Context;

/**
 * Target context.
 */
public class TargetContext implements Context<Object> {

    private final Object target;

    public TargetContext(final Object target) {
        this.target = target;
    }

    @Override
    public Object get() {
        return target;
    }

}
