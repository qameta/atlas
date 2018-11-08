package io.qameta.atlas;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.annotations.AppiumDriverProvider;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * Mobile Page.
 */
public interface ScreenPage extends WrapsDriver, SearchContext {

    @AppiumDriverProvider
    AppiumDriver getWrappedDriver();

}
