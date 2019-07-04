package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.util.MethodInfo;

/**
 * Retryer.
 */
public interface Retryer {

    boolean shouldRetry(Throwable e, MethodInfo methodInfo) throws Throwable;

}
