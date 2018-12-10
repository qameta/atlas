package io.qameta.atlas.core.context;

import io.qameta.atlas.core.api.Context;
import io.qameta.atlas.core.api.Target;

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
