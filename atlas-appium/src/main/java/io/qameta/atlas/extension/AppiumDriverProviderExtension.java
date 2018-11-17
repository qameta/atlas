package io.qameta.atlas.extension;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.annotations.AppiumDriverProvider;
import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.context.AppiumDriverContext;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;

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
