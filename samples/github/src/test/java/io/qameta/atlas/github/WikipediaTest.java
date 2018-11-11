package io.qameta.atlas.github;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.atlas.AppiumDriverConfiguration;
import io.qameta.atlas.Atlas;
import io.qameta.atlas.Screen;
import io.qameta.atlas.github.mobile.config.MobileConfig;
import io.qameta.atlas.github.mobile.page.MainScreen;
import io.qameta.atlas.github.mobile.page.SearchScreen;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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
        } else {
            throw new UnsupportedOperationException("Set valid driver");
        }
        atlas = new Atlas(new AppiumDriverConfiguration(driver));

    }

    @Test
    public void simpleTestAndroid() {
        onMainScreen().searchWikipedia().click();
        onSearchScreen().search().sendKeys("Java");
    }

    @Test
    public void simpleTestIOS() {
        onMainScreen().button("Next").click();
        onMainScreen().button("Next").click();
        onMainScreen().button("Next").click();
        onMainScreen().button("Get started").click();
        onMainScreen().searchWikipedia().click();
    }

    @Test
    public void simpleSwipeToDown() throws InterruptedException {
        //driver.hideKeyboard();
        //onMainScreen().button("Skip").click();
        onMainScreen().searchWikipedia().click();
        onSearchScreen().search().sendKeys("Atlas");
        TimeUnit.SECONDS.sleep(5);
        onSearchScreen().item("Atlas Mountains").swipeUpOn().click();
        TimeUnit.SECONDS.sleep(5);
    }


    @After
    public void stopDriver() {
        this.driver.quit();
    }

    private MainScreen onMainScreen() {
        return onPage(MainScreen.class);
    }

    private SearchScreen onSearchScreen() {
        return onPage(SearchScreen.class);
    }

    private <T extends Screen> T onPage(Class<T> page) {
        return atlas.create(driver, page);
    }
}
