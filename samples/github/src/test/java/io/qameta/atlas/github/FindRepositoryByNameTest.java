package io.qameta.atlas.github;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.WebDriverConfiguration;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.github.web.page.MainPage;
import io.qameta.atlas.github.web.page.SearchPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.should;
import static ru.yandex.qatools.matchers.webdriver.driver.HasTextMatcher.textOnCurrentPage;

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
    public void simpleTest()  {
        onMainPage().open("https://github.com");
        onMainPage().header().searchInput().sendKeys("Atlas");
        onMainPage().header().searchInput().submit();
        onSearchPage().repositories().waitUntil(hasSize(10));
    }

    @Test
    @Ignore
    public void simpleTestWithJS()  {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        onMainPage().open("https://github.com");
        js.executeScript("arguments[0].click();", onMainPage().trial());
        assertThat(driver, should(textOnCurrentPage(is(containsString("Start your 45-day free trial")))));
    }

    @Test
    @Ignore
    public void secondTestWithJS() {
        onMainPage().open("https://github.com");
        onMainPage().trial().executeScript("arguments[0].click();");
        assertThat(driver, should(textOnCurrentPage(is(containsString("Start your 45-day free trial")))));
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
