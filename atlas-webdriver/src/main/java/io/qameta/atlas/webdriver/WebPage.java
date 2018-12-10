package io.qameta.atlas.webdriver;

import io.qameta.atlas.webdriver.extension.DriverProvider;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * Web Page.
 */
public interface WebPage extends WrapsDriver, SearchContext {

    @DriverProvider
    WebDriver getWrappedDriver();

    default void open(String url) {
        getWrappedDriver().get(url);
    }

}
