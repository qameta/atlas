package io.qameta.appium.extension;

import io.appium.java_client.AppiumDriver;
import io.qameta.appium.annotations.AppiumDriverProvider;
import io.qameta.core.api.MethodExtension;
import io.qameta.appium.context.AppiumDriverContext;
import io.qameta.core.internal.Configuration;
import io.qameta.core.util.MethodInfo;

import java.lang.reflect.Method;

/**
 * Appium Driver provider extension.
 */
public class AppiumDriverProviderExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(AppiumDriverProvider.class);
    }

    @Override
    public AppiumDriver invoke(final Object proxy, final MethodInfo methodInfo, final Configuration config) {
        return config.getContext(AppiumDriverContext.class).get().getValue();
    }
}
