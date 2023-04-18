package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.context.TargetContext;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;

import java.lang.reflect.Method;

/**
 * GetWrappedElement method extension.
 */
public class WrappedElementMethodExtension implements MethodExtension {

    private static final String GET_WRAPPED_ELEMENT = "getWrappedElement";

    @Override
    public boolean test(final Method method) {
        return GET_WRAPPED_ELEMENT.equals(method.getName());
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        return configuration.requireContext(TargetContext.class).getValue().instance();
    }
}
