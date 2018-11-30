package io.qameta.atlas.appium.context;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.core.api.Context;

/**
 * AppiumDriverContext io.qameta.atlas.context.
 * Used to save AppiumDriver instance to {@link io.qameta.atlas.core.internal.Configuration}.
 */
public class AppiumDriverContext implements Context<AppiumDriver> {

    private final AppiumDriver appiumDriver;

    public AppiumDriverContext(final AppiumDriver mobileDriver) {
        this.appiumDriver = mobileDriver;
    }

    @Override
    public AppiumDriver getValue() {
        return appiumDriver;
    }
}
