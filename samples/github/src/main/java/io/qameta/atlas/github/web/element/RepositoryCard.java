package io.qameta.atlas.github.web.element;

import io.qameta.atlas.AtlasWebElement;
import io.qameta.atlas.extension.FindBy;

/**
 * Repository card.
 */
public interface RepositoryCard extends AtlasWebElement<RepositoryCard> {

    @FindBy(".//h3")
    AtlasWebElement title();

}
