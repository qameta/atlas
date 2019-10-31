package io.qameta.atlas.core.internal;

import java.util.List;

/**
 * Retryer.
 */
public interface Retryer {

    boolean shouldRetry(final Long start, Throwable e) throws Throwable;

    default boolean shouldRetry(final Long start, final Long timeout, final Long polling,
                                final List<Class<? extends Throwable>> ignoring, final Throwable e) {
        final long current = System.currentTimeMillis();
        if (!(ignoring.stream().anyMatch(clazz -> clazz.isInstance(e)) && start + timeout < current)) {
            try {
                Thread.sleep(polling);
                return true;
            } catch (InterruptedException i) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    void timeoutInSeconds(int seconds);

}
