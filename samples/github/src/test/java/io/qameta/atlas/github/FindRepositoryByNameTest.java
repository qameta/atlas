package io.qameta.atlas.github;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.core.Atlas;
import io.qameta.webdriver.WebDriverConfiguration;
import io.qameta.webdriver.WebPage;
import io.qameta.atlas.github.web.page.MainPage;
import io.qameta.atlas.github.web.page.SearchPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.Matchers.hasSize;

/**
 * Demo of simple test. Using atlas-webdriver.
 */
public class FindRepositoryByNameTest {

    private WebDriver driver;

    private Atlas atlas;

    @Before
    public void startDriver() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        atlas = new Atlas(new WebDriverConfiguration(driver));
    }

    @Test
    @Ignore
    public void simpleTest() throws InterruptedException {
        onMainPage().open("https://github.com");
        onMainPage().header().searchInput().sendKeys("Atlas");
        onMainPage().header().searchInput().submit();

        onSearchPage().repositories().waitUntil(hasSize(10));
    }

    @After
    public void stopDriver() {
        this.driver.quit();
    }

    private MainPage onMainPage() {
        return onPage(MainPage.class);
    }

    private SearchPage onSearchPage() {
        return onPage(SearchPage.class);
    }

    private <T extends WebPage> T onPage(Class<T> page) {
        return atlas.create(driver, page);
    }
}
