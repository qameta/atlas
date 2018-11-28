package io.qameta.atlas.github.web.element;


import io.qameta.webdriver.AtlasWebElement;
import io.qameta.webdriver.extension.FindBy;

/**
 * Header of web page.
 */
public interface Header extends AtlasWebElement {

    @FindBy(".//input[contains(@class,'header-search-input')]")
    AtlasWebElement searchInput();

}
