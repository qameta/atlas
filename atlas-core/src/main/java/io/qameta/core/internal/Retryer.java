package io.qameta.core.internal;

/**
 * Retryer.
 */
public interface Retryer {

    boolean shouldRetry(Throwable e) throws Throwable;

}
