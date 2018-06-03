package io.qameta.atlas.extensions;

import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.exception.WaitUntilException;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.lang.reflect.Method;

import static io.qameta.atlas.util.MethodInfoUtils.getMatcher;
import static io.qameta.atlas.util.MethodInfoUtils.getMessage;

/**
 * WaitUntil method extension for {@link io.qameta.atlas.AtlasWebElement}.
 */
public class WaitUntilMethodExtension implements MethodExtension {

    private static final String WAIT_UNTIL = "waitUntil";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(WAIT_UNTIL);
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
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
