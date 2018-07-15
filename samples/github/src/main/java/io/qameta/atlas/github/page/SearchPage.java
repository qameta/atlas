package io.qameta.atlas.github.page;

import io.qameta.atlas.ElementsCollection;
import io.qameta.atlas.WebPage;
import io.qameta.atlas.extension.FindBy;
import io.qameta.atlas.github.element.RepositoryCard;
import io.qameta.atlas.github.layout.WithHeader;

public interface SearchPage extends WebPage, WithHeader {

    @FindBy(".//ul[contains(@class, 'repo-list')]//div[contains(@class, 'repo-list-item')]")
    ElementsCollection<RepositoryCard> repositories();

}
