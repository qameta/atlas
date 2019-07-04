package io.qameta.atlas.core.internal;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Retryer with default values.
 */
public class EmptyRetryer extends TimeBasedRetryer {

    public EmptyRetryer() {
        setTimeOutInSeconds(5);
        setPolling(1000);
        addAllToIgnore(Stream.of(Throwable.class).collect(Collectors.toSet()));
    }

}
