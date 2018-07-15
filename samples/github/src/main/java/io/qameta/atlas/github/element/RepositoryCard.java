package io.qameta.atlas.github.element;

import io.qameta.atlas.AtlasWebElement;
import io.qameta.atlas.extension.FindBy;

public interface RepositoryCard extends AtlasWebElement<RepositoryCard> {

    @FindBy(".//h3")
    AtlasWebElement title();

}
