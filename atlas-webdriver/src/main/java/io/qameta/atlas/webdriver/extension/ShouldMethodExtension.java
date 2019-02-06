package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.lang.reflect.Method;

/**
 * Should method extension for {@link io.qameta.atlas.webdriver.AtlasWebElement}.
 */
public class ShouldMethodExtension implements MethodExtension {

    private static final String SHOULD = "should";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(SHOULD);
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        final String message = methodInfo.getParameter(String.class)
                .orElse("");
        final Matcher matcher = methodInfo.getParameter(Matcher.class)
                .orElseThrow(() -> new IllegalStateException("Unexpected method signature"));

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
