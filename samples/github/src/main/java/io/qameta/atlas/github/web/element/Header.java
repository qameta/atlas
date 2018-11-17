package io.qameta.atlas.github.web.element;


import io.qameta.atlas.AtlasWebElement;
import io.qameta.atlas.extension.FindBy;

/**
 * Header of web page.
 */
public interface Header extends AtlasWebElement {

    @FindBy(".//input[contains(@class,'header-search-input')]")
    AtlasWebElement searchInput();

}
