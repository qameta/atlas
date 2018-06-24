package io.qameta.atlas.context;

import io.qameta.atlas.api.Context;
import io.qameta.atlas.api.Target;

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
