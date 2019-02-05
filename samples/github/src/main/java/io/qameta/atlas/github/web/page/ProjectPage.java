package io.qameta.atlas.github.web.page;

import io.qameta.atlas.github.web.layout.WithHeader;
import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;

/**
 * Project page.
 */
public interface ProjectPage extends WebPage, WithHeader {

    @FindBy("//a[contains(.,'contributors')]")
    AtlasWebElement contributors();

}
