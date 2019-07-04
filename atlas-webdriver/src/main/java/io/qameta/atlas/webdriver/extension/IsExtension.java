package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.context.RetryerContext;
import io.qameta.atlas.core.context.TargetContext;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.internal.Retryer;
import io.qameta.atlas.core.internal.TimeBasedRetryer;
import io.qameta.atlas.core.util.MethodInfo;
import org.hamcrest.Matcher;
import org.openqa.selenium.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Extension for momentary checking atlas web elements.
 */
public class IsExtension implements MethodExtension {

    private final Set<Class<? extends Throwable>> requiredToIgnoring;

    public IsExtension() {
        this(NotFoundException.class, NoSuchElementException.class, StaleElementReferenceException.class);
    }

    @SafeVarargs private IsExtension(final Class<? extends Throwable>... requiredToIgnor) {
        requiredToIgnoring = Stream.of(requiredToIgnor).collect(Collectors.toSet());
    }

    @Override
    public boolean test(final Method method) {
        return getDelegate(method) != null && method.getDeclaringClass().isAssignableFrom(WebElement.class);
    }

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration configuration) {
        assert proxy instanceof SearchContext;
        final Retryer retryer = configuration.requireContext(RetryerContext.class).getValue();
        if (retryer instanceof TimeBasedRetryer) {
            final TimeBasedRetryer timeBasedRetryer = (TimeBasedRetryer) retryer;
            final boolean suppressThrowable = suppressThrowable(timeBasedRetryer, requiredToIgnoring);
            if (suppressThrowable) {
                final Configuration child = configuration.child();
                child.registerContext(new RetryerContext(retryer));
                return new Atlas(child).create(proxy, Boolean.class);
            }
        }

        final Delegate delegate = getDelegate(methodInfo.getMethod());
        final TargetContext targetContext = configuration.requireContext(TargetContext.class);
        final boolean result;
        switch (delegate) {
            case exists:
                result = exist(targetContext);
                break;
            case isDisplayed:
                result = isDisplayed(targetContext);
                break;
            case is:
                result = matching(proxy, methodInfo, false);
                break;
            case isNot:
                result = matching(proxy, methodInfo, true);
                break;
            default:
                throw new IllegalStateException("Unexpected delegate value: " + delegate);
        }
        return result;
    }

    @SuppressWarnings("PMD.IdenticalCatchBranches")
    private Boolean isDisplayed(final TargetContext targetContext) {
        boolean result;
        try {
            result = ((WebElement) targetContext.getValue().instance()).isDisplayed();
        } catch (StaleElementReferenceException e) {
            result = false;
        } catch (NoSuchElementException e) {
            result = true;
        }
        return result;
    }

    private Boolean exist(final TargetContext targetContext) {
        boolean result;
        try {
            final WebElement webElement = (WebElement) targetContext.getValue().instance();
            webElement.isDisplayed();
            result = true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            result = false;
        }
        return result;
    }


    /**
     * Add throwable suppressor, for skip reTry timeouts in all nested calls.
     */
    private boolean suppressThrowable(final TimeBasedRetryer retryer, final Set<Class<? extends Throwable>> classes) {
        final Set<Class<? extends Throwable>> ignoring = retryer.getIgnoring();
        boolean override = false;
        for (Class<? extends Throwable> requiredToIgnore : classes) {
            if (!ignoring.contains(requiredToIgnore)) {
                override = true;
                ignoring.add(requiredToIgnore);
            }
        }
        return override;
    }

    private boolean matching(final Object proxy, final MethodInfo methodInfo, final boolean reverse) {
        final Matcher matcher = methodInfo
                .getParameter(Matcher.class)
                .orElseThrow(() -> new IllegalStateException("Unexpected method signature"));
        return reverse != matcher.matches(proxy);
    }

    /**
     * Check delegate names and check [boolean] return type.
     */
    private Delegate getDelegate(final Method method) {
        return
                Arrays.stream(Delegate.values())
                        .filter(value -> method.getName().equals(value.name())).findFirst()
                        .filter(delegate -> method.getReturnType().isAssignableFrom(Boolean.class))
                        .orElse(null);
    }

    /**
     * Methods names for interception.
     */
    @SuppressWarnings("PMD")
    enum Delegate {
        exists,
        isDisplayed,
        is,
        isNot
    }

}

