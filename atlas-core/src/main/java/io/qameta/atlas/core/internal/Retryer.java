package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.api.Context;

/**
 * Retryer.
 */
public interface Retryer extends Context<Retryer> {

    boolean shouldRetry(Throwable e) throws Throwable;

}
