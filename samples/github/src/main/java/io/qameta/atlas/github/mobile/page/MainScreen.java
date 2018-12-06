package io.qameta.atlas.github.mobile.page;

import io.qameta.atlas.appium.AtlasMobileElement;
import io.qameta.atlas.appium.Screen;
import io.qameta.atlas.appium.annotations.AndroidFindBy;
import io.qameta.atlas.appium.annotations.IOSFindBy;
import io.qameta.atlas.core.api.Retry;
import io.qameta.atlas.webdriver.extension.Param;

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
