package io.qameta.atlas.github.mobile.page;

import io.qameta.appium.AtlasMobileElement;
import io.qameta.appium.Screen;
import io.qameta.appium.annotations.AndroidFindBy;
import io.qameta.appium.annotations.IOSFindBy;
import io.qameta.core.api.Retry;
import io.qameta.webdriver.extension.Param;

/**
 * Main screen of mobile application.
 */
public interface MainScreen extends Screen {

    @Retry(timeout = 20000L)
    @IOSFindBy(xpath = "//XCUIElementTypeSearchField[@name='Search Wikipedia']")
    @AndroidFindBy(xpath = "//*[contains(@text, 'Search Wikipedia')]")
    AtlasMobileElement searchWikipedia();

    @Retry
    @IOSFindBy(id = "{{ value }}")
    AtlasMobileElement button(@Param("value") String value);
}
