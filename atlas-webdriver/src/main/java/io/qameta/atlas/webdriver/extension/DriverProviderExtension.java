package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.webdriver.context.WebDriverContext;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

/**
 * Driver provider extension.
 */
public class DriverProviderExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(DriverProvider.class);
    }

    @Override
    public WebDriver invoke(final Object proxy, final MethodInfo methodInfo, final Configuration config)
            throws Throwable {
        return config.getContext(WebDriverContext.class).get().getValue();
    }
}
