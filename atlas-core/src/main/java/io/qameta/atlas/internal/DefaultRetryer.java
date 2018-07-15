package io.qameta.atlas.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Retryer.
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class DefaultRetryer implements Retryer {

    private final List<Class<? extends Throwable>> ignoring;

    private final Long start;

    private Long timeout;

    private Long polling;

    public DefaultRetryer() {
        this.ignoring = new ArrayList<>();
        this.timeout = TimeUnit.SECONDS.toMillis(5);
        this.polling = TimeUnit.MILLISECONDS.toMillis(250);
        this.start = System.currentTimeMillis();
    }

    public void ignore(final Class<? extends Throwable> throwable) {
        this.ignoring.add(throwable);
    }

    public void timeout(final Long millis) {
        this.timeout = millis;
    }

    public void polling(final Long polling) {
        this.polling = polling;
    }

    public boolean shouldRetry(final Throwable e) {
        if (ignoring.stream().anyMatch(clazz -> clazz.isInstance(e))
                && start + timeout < System.currentTimeMillis()) {
            try {
                Thread.sleep(polling);
                return false;
            } catch (InterruptedException i) {
                Thread.currentThread().interrupt();
            }
        }
        return true;
    }

}
