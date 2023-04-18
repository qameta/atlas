package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
import static org.mockito.Mockito.*;

public class ExecuteJScriptMethodExtensionTest {

    private AtlasWebElement atlasWebElement;
    private WebDriver driver;

    @Before
    public void initElements() {
        driver = mock(RemoteWebDriver.class, withSettings().extraInterfaces(WebDriver.class));
        WebElement originWebElement = mockWebElement();
        atlasWebElement = new Atlas(new WebDriverConfiguration(driver))
                .create(originWebElement, AtlasWebElement.class);
    }

    @Test
    public void getWrappedElementMethodTest() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String bodyScript = "arguments[0].click();";
        atlasWebElement.executeScript(bodyScript);
        verify(js, times(1)).executeScript(bodyScript, atlasWebElement);
    }
}
