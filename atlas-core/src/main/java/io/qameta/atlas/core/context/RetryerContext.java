package io.qameta.atlas.core.context;

import io.qameta.atlas.core.api.Context;
import io.qameta.atlas.core.internal.Retryer;

/**
 * Retry context.
 */
public class RetryerContext implements Context<Retryer> {

    private final Retryer retry;

    public RetryerContext(final Retryer retry) {
        this.retry = retry;
    }

    @Override
    public Retryer getValue() {
        return retry;
    }

}
