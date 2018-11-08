package io.qameta.atlas.github.mobile.page;

import io.qameta.atlas.AtlasMobileElement;
import io.qameta.atlas.ScreenPage;
import io.qameta.atlas.annotations.AndroidFindBy;

public interface SearchPage extends ScreenPage {

    @AndroidFindBy(xpath = "//*[contains(@text, 'Search…')]")
    AtlasMobileElement searchInput();

    @AndroidFindBy(id = "search_close_btn")
    AtlasMobileElement closeBtn();

}
