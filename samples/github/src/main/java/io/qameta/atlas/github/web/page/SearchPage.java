package io.qameta.atlas.github.web.page;

import io.qameta.webdriver.ElementsCollection;
import io.qameta.webdriver.WebPage;
import io.qameta.webdriver.extension.FindBy;
import io.qameta.atlas.github.web.element.RepositoryCard;
import io.qameta.atlas.github.web.layout.WithHeader;

/**
 * Search page.
 */
public interface SearchPage extends WebPage, WithHeader {

    @FindBy(".//ul[contains(@class, 'repo-list')]//li[contains(@class, 'repo-list-item')]")
    ElementsCollection<RepositoryCard> repositories();

}
