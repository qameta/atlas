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
import static org.mockito.Mockito.*;

public class PageExtensionTest {

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
        TestSite onSite =
                new Atlas(new WebDriverConfiguration(driver, "https://github.com"))
                        .create(driver, TestSite.class);

        onSite.onMainPage("кириллица").atlasWebElement.click();
        verify(driver, times(1)).get(exceptedURI);
    }

    @Test
    public void shouldHandleDefaultPathOfPage() {
        when(driver.getTitle()).thenReturn("atlas");
        TestSiteWithDefaultPage onSite =
                new Atlas(new WebDriverConfiguration(driver))
                        .create(driver, TestSiteWithDefaultPage.class);

        String title = onSite.onMainPage().getWrappedDriver().getTitle();
        assertEquals("atlas", title);
    }


    @Test
    public void shouldHandleQueryParams() {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        TestSite onSite =
                new Atlas(new WebDriverConfiguration(driver, "https://github.com"))
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
        assertEquals(3, capturedStringArg.split("&").length);
    }


    @Test(expected = AtlasException.class)
    public void siteWithOutAnyBaseURIShouldThrowException() {
        System.getProperties().clear();
        TestSiteWithOutAnyURI siteWithOutUrl =
                new Atlas(new WebDriverConfiguration(mockWebDriver()))
                        .create(driver, TestSiteWithOutAnyURI.class);
        siteWithOutUrl.onMainPage("null");
    }

    @Test
    public void setBaseURIMethodShouldHaveWebSite() {
        TestSiteWithOutAnyURI siteWithOutUrl =
                new Atlas(new WebDriverConfiguration(driver, "https://github.com"))
                        .create(driver, TestSiteWithOutAnyURI.class);

        siteWithOutUrl.onMainPage("zero").atlasWebElement.click();
        verify(driver, times(1)).get("https://github.com/search?a=zero");
    }

    @Test
    public void shouldHandlePathParams() {
        TestSiteWithPathParams siteWithPath =
                new Atlas(new WebDriverConfiguration(driver, "https://github.com"))
                        .create(driver, TestSiteWithPathParams.class);
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("first", "value-1");

        Map<String, String> queryMap1 = new HashMap<>();
        queryMap1.put("second", "value-2");

        siteWithPath.onMainPage(1100109, "Atlas", 100, queryMap, queryMap1).atlasWebElement.click();
        verify(driver, times(1)).get("https://github.com/users/1100109/Atlas?q=100&first=value-1&second=value-2");
    }


    @Test
    public void shouldHandleUrlWithUserAndPassword() {
        TestSiteWithOutAnyURI siteWithUserAndPass =
                new Atlas(new WebDriverConfiguration(driver, "http://username:password@example.com/"))
                        .create(driver, TestSiteWithOutAnyURI.class);

        siteWithUserAndPass.onMainPage("zero").atlasWebElement.click();
        verify(driver, times(1)).get("http://username:password@example.com/search?a=zero");
    }


    @Test
    public void shouldHandleUrlWithPort() {
        TestSiteWithOutAnyURI siteWithUserAndPass =
                new Atlas(new WebDriverConfiguration(driver, "http://example.com:8443/"))
                        .create(driver, TestSiteWithOutAnyURI.class);

        siteWithUserAndPass.onMainPage("zero").atlasWebElement.click();
        verify(driver, times(1)).get("http://example.com:8443/search?a=zero");
    }

    public interface TestSite extends WebSite {
        @Page(url = "total")
        MainPage onMainPage(@Query("q") String value, @QueryMap Map<String, String> queryMap);

        @Page(url = "search")
        MainPage onMainPage(@Query("abs") String value);
    }

    public interface TestSiteWithPathParams extends WebSite {
        @Page(url = "users/{id}/{project}")
        MainPage onMainPage(@Path("id") long customerId, @Path("project") String name, @Query("q") Integer value, @QueryMap Map<String, String> queryMap, @QueryMap Map<String, String> queryMap1);
    }

    public interface TestSiteWithOutAnyURI extends WebSite {
        @Page(url = "search")
        MainPage onMainPage(@Query("a") String value);
    }


    public interface TestSiteWithDefaultPage extends WebSite {
        @Page
        MainPage onMainPage();
    }

    public interface MainPage extends WebPage {
        AtlasWebElement atlasWebElement = mockAtlasWebElement();
    }

}
