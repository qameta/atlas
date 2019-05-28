package io.qameta.atlas.appium.testdata;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.atlas.appium.AtlasMobileElement;
import io.qameta.atlas.webdriver.AtlasWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.WrapsDriver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

/**
 * Factory of mocks for AppiumDriver, MobileElement, AtlasMobileElement.
 */
public final class ObjectFactory {

    private ObjectFactory() {
    }

    public static AppiumDriver mockAppiumDriver() {
        return mock(AppiumDriver.class, withSettings().extraInterfaces(WrapsDriver.class, HasInputDevices.class));
    }

    public static AppiumDriver mockAndroidDriver() {
        return mock(AndroidDriver.class, withSettings().extraInterfaces(WrapsDriver.class, HasInputDevices.class));
    }


    public static IOSDriver mockIOSDriver() {
        return mock(IOSDriver.class, withSettings().extraInterfaces(WrapsDriver.class, HasInputDevices.class));
    }

    public static WebElement mockWebElement() {
        return mock(WebElement.class);
    }

    public static AtlasMobileElement mockAtlasMobileElement() {
        return mock(AtlasMobileElement.class, withSettings().extraInterfaces(AtlasWebElement.class));
    }
}
