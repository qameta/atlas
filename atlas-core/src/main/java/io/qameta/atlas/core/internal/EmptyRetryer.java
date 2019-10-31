package io.qameta.atlas.core.internal;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Retryer with default values.
 */
public class EmptyRetryer implements Retryer {

    private final Long polling;
    private final List<Class<? extends Throwable>> ignoring;
    private Long timeout;

    public EmptyRetryer() {
        this.timeout = 5000L;
        this.polling = 1000L;
        this.ignoring = Collections.singletonList(Throwable.class);
    }

    @Override
    public boolean shouldRetry(final Throwable e) {
        return shouldRetry(System.currentTimeMillis(), timeout, polling, ignoring, e);

    }

    @Override
    public void timeoutInSeconds(final int seconds) {
        this.timeout = TimeUnit.SECONDS.toMillis(seconds);
    }

}
