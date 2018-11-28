package io.qameta.webdriver.extension;

import io.qameta.core.api.MethodExtension;
import io.qameta.webdriver.exception.WaitUntilException;
import io.qameta.core.internal.Configuration;
import io.qameta.core.util.MethodInfo;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.lang.reflect.Method;

import static io.qameta.webdriver.util.MethodInfoUtils.getMatcher;
import static io.qameta.webdriver.util.MethodInfoUtils.getMessage;

/**
 * WaitUntil method extension for {@link io.qameta.webdriver.AtlasWebElement}.
 */
public class WaitUntilMethodExtension implements MethodExtension {

    private static final String WAIT_UNTIL = "waitUntil";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(WAIT_UNTIL);
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        final Object[] args = methodInfo.getArgs();

        final String message = getMessage(args);
        final Matcher matcher = getMatcher(args);

        if (!matcher.matches(proxy)) {
            final StringDescription description = new StringDescription();
            description.appendText(message)
                    .appendText("\nExpected: ")
                    .appendDescriptionOf(matcher)
                    .appendText("\n     but: ");
            matcher.describeMismatch(proxy, description);
            throw new WaitUntilException(description.toString());
        }
        return proxy;
    }

}
