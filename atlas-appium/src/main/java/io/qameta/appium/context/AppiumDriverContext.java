package io.qameta.appium.context;

import io.appium.java_client.AppiumDriver;
import io.qameta.core.api.Context;

/**
 * AppiumDriverContext io.qameta.atlas.context.
 * Used to save AppiumDriver instance to {@link io.qameta.core.internal.Configuration}.
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
