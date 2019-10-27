package io.qameta.atlas.github;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.github.web.element.RepositoryCard;
import io.qameta.atlas.github.web.site.GitHubSite;
import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.ElementsCollection;
import io.qameta.atlas.webdriver.WebDriverConfiguration;
import io.qameta.atlas.webdriver.extension.PageExtension;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.Matchers.*;
import static ru.yandex.qatools.matchers.webdriver.TextMatcher.text;

/**
 * Demo of simple tests with WebSite implementation.
 * This feature allow you to make one point of your system under tests (website).
 * In additional you can open any Page through domain URL.
 * With WebSite implementation look to
 * <p>
 * {@link io.qameta.atlas.webdriver.extension.Page},
 * {@link io.qameta.atlas.webdriver.extension.PageExtension}, {@link io.qameta.atlas.webdriver.extension.Path},
 * {@link io.qameta.atlas.webdriver.extension.Query}, {@link io.qameta.atlas.webdriver.extension.QueryMap},
 * {@link io.qameta.atlas.webdriver.extension.Page}, {@link PageExtension}.
 */
public class WebSiteTest {

    private Atlas atlas;
    private WebDriver driver;

    @Before
    public void startDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        atlas = new Atlas(new WebDriverConfiguration(driver, "https://github.com"));
    }

    @Ignore
    @Test
    public void simpleWebSiteTest() {
        onSite().onMainPage().open();
        onSite().onMainPage().header().searchInput().sendKeys("Atlas");
        onSite().onMainPage().header().searchInput().submit();
        onSite().onSearchPage("Junit 5").repositories().waitUntil(hasSize(10));
    }

    @Ignore
    @Test
    public void baseUriWebSiteTest() {
        onSite().onSearchPage("Junit 5").repositories().waitUntil(hasSize(10))
                .should(everyItem(text(containsString("junit"))));
    }

    @Ignore
    @Test
    public void usePathWebSiteTest() {
        onSite().onProjectPage("qameta", "atlas").contributors().click();
        onSite().onContributorsPage().hovercards().waitUntil(hasSize(4));
    }

    @Ignore
    @Test
    public void shouldExtractRepositoriesInfo() {
        ElementsCollection<String> nameOfRepositories = onSite().onSearchPage("Junit 5").repositories()
                .extract(RepositoryCard::title)
                .extract(AtlasWebElement::getText);
        nameOfRepositories.should(everyItem(anyOf(containsString("junit5"),
                containsString("jupiter"))));
    }


    private GitHubSite onSite() {
        return atlas.create(driver, GitHubSite.class);
    }

    @After
    public void stopDriver() {
        driver.quit();
    }
}
