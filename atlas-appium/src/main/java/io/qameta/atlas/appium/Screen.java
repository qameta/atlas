package io.qameta.atlas.appium;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.appium.annotations.AppiumDriverProvider;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WrapsDriver;

/**
 * Mobile Page.
 */
public interface Screen extends WrapsDriver, SearchContext {

    @AppiumDriverProvider
    AppiumDriver getWrappedDriver();

}
