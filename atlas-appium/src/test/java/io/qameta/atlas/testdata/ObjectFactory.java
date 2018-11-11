package io.qameta.atlas.testdata;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.atlas.AtlasMobileElement;
import io.qameta.atlas.AtlasWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

public class ObjectFactory {

    private ObjectFactory() {
    }

    public static AppiumDriver mockAppiumDriver() {
        return mock(AppiumDriver.class, withSettings().extraInterfaces(HasInputDevices.class));
    }

    public static MobileElement mockAppiumElement() {
        return mock(MobileElement.class, withSettings().extraInterfaces(WebElement.class));
    }

    public static AtlasMobileElement mockAtlasMobileElement() {
        return mock(AtlasMobileElement.class, withSettings().extraInterfaces(AtlasWebElement.class));
    }
}
