package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.api.Retry;
import io.qameta.atlas.core.context.RetryerContext;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.internal.Retryer;
import io.qameta.atlas.core.internal.TimeBasedRetryer;
import io.qameta.atlas.core.util.MethodInfo;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Extension for pre-processing @Retry annotations.
 */
public class RetryAnnotationExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return Optional.ofNullable(method.getAnnotation(Retry.class)).isPresent();
    }

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration configuration) {
        final Retry retry = methodInfo.getMethod().getAnnotation(Retry.class);
        assert retry != null : "@Retry annotation can't be null";
        final Retryer retryer = configuration.requireContext(RetryerContext.class).getValue();
        if (retryer instanceof TimeBasedRetryer) {
            final TimeBasedRetryer timeBasedRetryer = (TimeBasedRetryer) retryer;
            final long timeout = retry.timeout();
            if (timeout >= 0) {
                timeBasedRetryer.setTimeOut(timeout);
            }
            final long polling = retry.polling();
            if (polling >= 0) {
                timeBasedRetryer.setPolling(polling);
            }
            timeBasedRetryer.addAllToIgnore(Stream.of(retry.ignoring()).collect(Collectors.toSet()));
            final Configuration child = configuration.child();
            child.registerContext(new RetryerContext(retryer));
            return new Atlas(child).create(proxy, methodInfo.getMethod().getReturnType());
        } else {
            return new Atlas(configuration).create(proxy, methodInfo.getMethod().getReturnType());
        }
    }

}
