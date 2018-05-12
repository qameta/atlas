package io.qameta.atlas.extensions;

import io.qameta.atlas.api.Extension;
import io.qameta.atlas.exception.WaitUntilException;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.lang.reflect.Method;

import static io.qameta.atlas.util.MethodInfoUtils.getMatcher;
import static io.qameta.atlas.util.MethodInfoUtils.getMessage;

/**
 * @author kurau (Yuri Kalinin)
 */
public class WaitUntilExtension implements Extension {

    @Override
    public boolean test(final Method method) {
        return method.getName().equals("waitUntil");
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
