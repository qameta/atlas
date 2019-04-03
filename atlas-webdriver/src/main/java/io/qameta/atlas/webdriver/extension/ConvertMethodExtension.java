package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author artem.krosheninnikov
 */
public class ConvertMethodExtension implements MethodExtension {

    private static final String CONVERT = "convert";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(CONVERT)
                && List.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration config) {
        final Function converter = (Function) methodInfo.getArgs()[0];

        return ((List) proxy).stream().map(converter).collect(toList());
    }
}
