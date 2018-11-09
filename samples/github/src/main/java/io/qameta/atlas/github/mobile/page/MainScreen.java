package io.qameta.atlas.github.mobile.page;

import io.qameta.atlas.AtlasMobileElement;
import io.qameta.atlas.ScreenPage;
import io.qameta.atlas.annotations.AndroidFindBy;
import io.qameta.atlas.annotations.IOSFindBy;
import io.qameta.atlas.api.Retry;
import io.qameta.atlas.extension.Param;


public interface MainScreen extends ScreenPage {

    @Retry
    @IOSFindBy(xpath = "//XCUIElementTypeSearchField[@name='Search Wikipedia']")
    @AndroidFindBy(xpath = "//*[contains(@text, 'Search Wikipedia')]")
    AtlasMobileElement searchWikipediaInputInit();

    @Retry
    @IOSFindBy(id = "{{ value }}")
    AtlasMobileElement button(@Param("value") String value);

}
