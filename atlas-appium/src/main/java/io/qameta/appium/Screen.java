package io.qameta.appium;

import io.appium.java_client.AppiumDriver;
import io.qameta.appium.annotations.AppiumDriverProvider;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * Mobile Page.
 */
public interface Screen extends WrapsDriver, SearchContext {

    @AppiumDriverProvider
    AppiumDriver getWrappedDriver();

}
