package io.qameta.atlas.internal;

/**
 * Retryer.
 */
public interface Retryer {

    boolean shouldRetry(Throwable e) throws Throwable;

}
