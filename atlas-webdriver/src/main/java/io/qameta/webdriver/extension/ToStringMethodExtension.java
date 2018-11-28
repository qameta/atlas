package io.qameta.webdriver.extension;

import io.qameta.core.api.MethodExtension;
import io.qameta.core.context.TargetContext;
import io.qameta.core.internal.Configuration;
import io.qameta.core.util.MethodInfo;

import java.lang.reflect.Method;

/**
 * ToString method extension.
 */
public class ToStringMethodExtension implements MethodExtension {

    private static final String TO_STRING = "toString";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(TO_STRING);
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        return configuration.requireContext(TargetContext.class).getValue().name();
    }

}
