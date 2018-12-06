package io.qameta.atlas.core.internal;

/**
 * Retryer.
 */
public interface Retryer {

    boolean shouldRetry(Throwable e) throws Throwable;

}
