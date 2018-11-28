package io.qameta.atlas.github.web.element;

import io.qameta.webdriver.AtlasWebElement;
import io.qameta.webdriver.extension.FindBy;

/**
 * Repository card.
 */
public interface RepositoryCard extends AtlasWebElement<RepositoryCard> {

    @FindBy(".//h3")
    AtlasWebElement title();

}
