package io.qameta.atlas.context;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.api.Context;

/**
 * AppiumDriverContext io.qameta.atlas.context.
 * Used to save AppiumDriver instance to {@link io.qameta.atlas.internal.Configuration}.
 */
public class AppiumDriverContext implements Context<AppiumDriver> {

    private final AppiumDriver mobileDriver;

    public AppiumDriverContext(AppiumDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
    }

    @Override
    public AppiumDriver getValue() {
        return mobileDriver;
    }
}