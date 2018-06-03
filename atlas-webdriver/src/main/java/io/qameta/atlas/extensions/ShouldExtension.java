package io.qameta.atlas.extensions;

import io.qameta.atlas.api.MethodExtension;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.lang.reflect.Method;

import static io.qameta.atlas.util.MethodInfoUtils.getMatcher;
import static io.qameta.atlas.util.MethodInfoUtils.getMessage;

/**
 * Should method extension for {@link io.qameta.atlas.AtlasWebElement}.
 */
public class ShouldExtension implements MethodExtension {

    private static final String SHOULD = "should";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(SHOULD);
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
            throw new AssertionError(description.toString());
        }
        return proxy;
    }

}
