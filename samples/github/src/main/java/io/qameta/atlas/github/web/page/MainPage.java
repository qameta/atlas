package io.qameta.atlas.github.web.page;

import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.github.web.layout.WithHeader;
import io.qameta.atlas.webdriver.extension.FindBy;

/**
 * Main page of site.
 */
public interface MainPage extends WebPage, WithHeader {

    @FindBy("//a[contains(text(), 'Start a free trial')]")
    AtlasWebElement trial();

}
