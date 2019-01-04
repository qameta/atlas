package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.webdriver.context.WebDriverContext;
import io.qameta.atlas.webdriver.extension.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockAtlasWebElement;
import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebDriver;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class URLExtensionTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        this.driver = mockWebDriver();
    }

    @After
    public void stopDriver() {
        this.driver.quit();
    }


    @Test
    public void shouldHandleSingleQueryParam() {
        String exceptedURI = "https://github.com/search?abs=%D0%BA%D0%B8%D1%80%D0%B8%D0%BB%D0%BB%D0%B8%D1%86%D0%B0";
        TestSite onSite = new Atlas()
                .extension(new URLExtension())
                .extension(new DriverProviderExtension())
                .context(new WebDriverContext(driver))
                .create(driver, TestSite.class);

        onSite.onMainPage("кириллица").atlasWebElement.click();
        verify(driver, times(1)).get(exceptedURI);
    }


    @Test
    public void shouldHandleQueryParams() {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        TestSite onSite = new Atlas()
                .extension(new URLExtension())
                .extension(new DriverProviderExtension())
                .context(new WebDriverContext(driver))
                .create(driver, TestSite.class);

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("first", "value-1");
        queryMap.put("second", "value-2");
        onSite.onMainPage("100", queryMap).atlasWebElement.sendKeys("Use driver.get(...) and then sendKeys");
        verify(driver, times(1)).get(stringCaptor.capture());

        String capturedStringArg = stringCaptor.getValue();
        assertThat(capturedStringArg, allOf(containsString("total?"),
                containsString("q=100"),
                containsString("first=value-1"),
                containsString("second=value-2"),
                containsString("?")));
        assertEquals(capturedStringArg.split("&").length, 3);
    }


    @Test(expected = AtlasException.class)
    public void siteWithOutAnyBaseURIShouldThrowException() {
        TestSiteWithOutAnyURI siteWithOutUrl = new Atlas(new WebDriverConfiguration(mockWebDriver()))
                .create(driver, TestSiteWithOutAnyURI.class);
        siteWithOutUrl.onMainPage("null");
    }

    @Test
    public void setBaseURIMethodShouldHaveWebSite() {
        TestSiteWithOutAnyURI siteWithOutUrl = new Atlas()
                .extension(new URLExtension())
                .extension(new DriverProviderExtension())
                .extension(new DefaultSiteExtension())
                .context(new WebDriverContext(driver))
                .create(driver, TestSiteWithOutAnyURI.class);
        siteWithOutUrl.setBaseURI("https://github.com");
        siteWithOutUrl.onMainPage("zero").atlasWebElement.click();
        verify(driver, times(1)).get("https://github.com/search?a=zero");
    }

    @Test
    public void shouldHandlePathParams() {
        TestSiteWithPathParams siteWithPath = new Atlas()
                .extension(new URLExtension())
                .extension(new DriverProviderExtension())
                .extension(new DefaultSiteExtension())
                .context(new WebDriverContext(driver))
                .create(driver, TestSiteWithPathParams.class);
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("first", "value-1");

        siteWithPath.onMainPage(1100109, "Atlas", 100, queryMap).atlasWebElement.click();
        verify(driver, times(1)).get("https://github.com/users/1100109/Atlas?q=100&first=value-1");
    }

    @BaseURI("https://github.com")
    public interface TestSite extends WebSite {
        @URL("total")
        MainPage onMainPage(@Query("q") String value, @QueryMap Map<String, String> queryMap);

        @URL("search")
        MainPage onMainPage(@Query("abs") String value);
    }

    @BaseURI("https://github.com")
    public interface TestSiteWithPathParams extends WebSite {
        @URL("users/{id}/{project}")
        MainPage onMainPage(@Path("id") long customerId, @Path("project") String name, @Query("q") Integer value, @QueryMap Map<String, String> queryMap);
    }

    public interface TestSiteWithOutAnyURI extends WebSite {
        @URL("search")
        MainPage onMainPage(@Query("a") String value);
    }

    public interface MainPage extends WebPage {
        AtlasWebElement atlasWebElement = mockAtlasWebElement();
    }
}
