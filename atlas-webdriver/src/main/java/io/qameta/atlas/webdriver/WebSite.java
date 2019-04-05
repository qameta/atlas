package io.qameta.atlas.webdriver;

import io.qameta.atlas.webdriver.extension.DriverProvider;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * Web Site.
 */
public interface WebSite extends WrapsDriver, SearchContext {

    @DriverProvider
    @Override
    WebDriver getWrappedDriver();

}
