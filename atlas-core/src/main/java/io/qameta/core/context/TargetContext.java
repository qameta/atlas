package io.qameta.core.context;

import io.qameta.core.api.Context;
import io.qameta.core.api.Target;

/**
 * Target context.
 */
public class TargetContext implements Context<Target> {

    private final Target target;

    public TargetContext(final Target target) {
        this.target = target;
    }

    @Override
    public Target getValue() {
        return target;
    }

}
