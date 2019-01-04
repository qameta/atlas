package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import org.openqa.selenium.internal.WrapsDriver;

import java.lang.reflect.Method;

/**
 * Extension for methods with {@link Page} annotation.
 *
 */
public class PageExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(Page.class);
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        final WrapsDriver wrapsDriver = (WrapsDriver) proxy;
        return new Atlas(configuration).create(wrapsDriver.getWrappedDriver(),
                (Class<?>) methodInfo.getMethod().getReturnType());
    }
}
