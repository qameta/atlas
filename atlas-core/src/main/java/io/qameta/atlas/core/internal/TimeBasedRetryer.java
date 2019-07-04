package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.util.MethodInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Default Retryer based on timeout.
 */
public abstract class TimeBasedRetryer implements Retryer {

    private final Set<Class<? extends Throwable>> ignoring = new HashSet<>();
    private final Long start = System.currentTimeMillis();
    private Long timeout = 0L;
    private Long polling = 0L;

    @Override
    public boolean shouldRetry(final Throwable e, final MethodInfo methodInfo) {
        return shouldRetry(start, e);
    }

    public boolean shouldRetry(final Long start, final Throwable e) {
        return shouldRetry(start, getTimeout(), getPolling(), getIgnoring(), e);
    }

    public boolean shouldRetry(final Long start, final Long timeout, final Long polling,
                        final Set<Class<? extends Throwable>> ignoring, final Throwable e) {
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

    public void setTimeOutInSeconds(final int seconds) {
        setTimeOut(TimeUnit.SECONDS.toMillis(seconds));
    }

    public void setTimeOut(final long ms) {
        this.timeout = ms;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public long getPolling() {
        return polling;
    }

    public void setPolling(final long ms) {
        this.polling = ms;
    }

    public Set<Class<? extends Throwable>> getIgnoring() {
        return ignoring;
    }

    public void addAllToIgnore(final Set<Class<? extends Throwable>> toIgnoring) {
        ignoring.addAll(toIgnoring);
    }

}
