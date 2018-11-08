package io.qameta.atlas.github;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.atlas.AppiumDriverConfiguration;
import io.qameta.atlas.Atlas;
import io.qameta.atlas.ScreenPage;
import io.qameta.atlas.github.mobile.config.MobileConfig;
import io.qameta.atlas.github.mobile.page.MainScreen;
import io.qameta.atlas.github.mobile.page.SearchPage;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class WikipediaTest {

    private AppiumDriver driver;
    private Atlas atlas;
    private MobileConfig config = ConfigFactory.create(MobileConfig.class);

    @Before
    public void setUp() throws MalformedURLException {
        String platform = config.platformName().trim();
        URL url = new URL(config.url());
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if("android".equalsIgnoreCase(platform)) {
            desiredCapabilities.setCapability("platformName", config.platformName());
            desiredCapabilities.setCapability("deviceName", config.deviceName());
            desiredCapabilities.setCapability("platformVersion", config.platformVersion());
            desiredCapabilities.setCapability("appPackage", config.appPackage());
            desiredCapabilities.setCapability("appActivity", config.appActivity());
            desiredCapabilities.setCapability( "unicodeKeyboard", config.unicodeKeyboard());
            desiredCapabilities.setCapability("resetKeyboard", config.resetKeyboard());
            desiredCapabilities.setCapability("automationName", config.automationName());
            desiredCapabilities.setCapability("newCommandTimeout", config.newCommandTimeout());
            desiredCapabilities.setCapability("app", config.apkFile());
            driver = new AndroidDriver(url, desiredCapabilities);
        } else if("ios".equalsIgnoreCase(platform)) {
            desiredCapabilities.setCapability("platformName", config.platformName());
            desiredCapabilities.setCapability("deviceName", config.deviceIOSName());
            desiredCapabilities.setCapability("platformVersion", config.platformIOSVersion());
            desiredCapabilities.setCapability("newCommandTimeout", config.newCommandTimeout());
            desiredCapabilities.setCapability("app", config.appFile());
            driver = new IOSDriver(url, desiredCapabilities);
        }
        atlas = new Atlas(new AppiumDriverConfiguration(driver));

    }

    @Test
    public void simpleTest() {
        onMainScreen().searchWikipediaInputInit().click();
        onSearchScreen().searchInput().sendKeys("Java");
    }

    @Test
    public void simpleTestIOS() {
        onMainScreen().button("Next").click();
        onMainScreen().button("Next").click();
        onMainScreen().button("Next").click();
        onMainScreen().button("Get started").click();
        onMainScreen().searchWikipediaInputInit().click();
    }


    @After
    public void stopDriver() {
        this.driver.quit();
    }

    private MainScreen onMainScreen() {
        return onPage(MainScreen.class);
    }

    private SearchPage onSearchScreen() {
        return onPage(SearchPage.class);
    }

    private <T extends ScreenPage> T onPage(Class<T> page) {
        return atlas.create(driver, page);
    }
}
