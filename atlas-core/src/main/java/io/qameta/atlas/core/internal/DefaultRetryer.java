package io.qameta.atlas.core.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @deprecated
 * class constructor will be removed in the next release.
 * Now the default implementation always used from the Atlas context
 * see reference
 */
@Deprecated
public class DefaultRetryer extends TimeBasedRetryer {

    public DefaultRetryer(final Long timeout, final Long polling, final List<Class<? extends Throwable>> ignoring) {
        this(timeout, polling, new HashSet<>(ignoring));
    }

    private DefaultRetryer(final Long timeout, final Long polling, final Set<Class<? extends Throwable>> ignoring) {
        setTimeOut(timeout);
        setPolling(polling);
        addAllToIgnore(ignoring);
    }

}
