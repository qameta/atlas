package io.qameta.atlas.webdriver.testdata;

import io.qameta.atlas.webdriver.AtlasWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Locatable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

public class ObjectFactory {

    private ObjectFactory() {
    }

    public static WebDriver mockWebDriver() {
        return mock(WebDriver.class, withSettings().extraInterfaces(HasInputDevices.class));
    }

    public static WebElement mockWebElement() {
        return mock(WebElement.class, withSettings().extraInterfaces(Locatable.class));
    }

    public static AtlasWebElement mockAtlasWebElement() {
        return mock(AtlasWebElement.class, withSettings());
    }
}
