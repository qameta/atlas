package io.qameta.atlas.extension;

import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.context.WebDriverContext;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;
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
