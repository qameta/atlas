package io.qameta.atlas.github.mobile.page;

import io.qameta.atlas.appium.AtlasMobileElement;
import io.qameta.atlas.appium.Screen;
import io.qameta.atlas.appium.annotations.AndroidFindBy;
import io.qameta.atlas.appium.annotations.IOSFindBy;
import io.qameta.atlas.core.api.Retry;
import io.qameta.atlas.webdriver.extension.Param;

/**
 * Search screen of mobile application.
 */
public interface SearchScreen extends Screen {

    @Retry(timeout = 20000L)
    @AndroidFindBy(xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_container']"
            + "//*[contains(@text, '{{ value }}')]")
    @IOSFindBy(xpath = "//XCUIElementTypeLink[contains(@name, '{{ value }}')]")
    AtlasMobileElement item(@Param("value") String value);

    @IOSFindBy(xpath = "//XCUIElementTypeApplication[@name='Wikipedia']//XCUIElementTypeSearchField")
    @AndroidFindBy(xpath = "//*[contains(@text, 'Search…')]")
    AtlasMobileElement search();

    @AndroidFindBy(id = "search_close_btn")
    AtlasMobileElement close();

}
