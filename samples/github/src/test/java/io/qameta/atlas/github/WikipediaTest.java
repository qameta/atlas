package io.qameta.atlas.github;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.atlas.appium.AppiumDriverConfiguration;
import io.qameta.atlas.appium.Screen;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.github.mobile.config.MobileConfig;
import io.qameta.atlas.github.mobile.page.ArticleScreen;
import io.qameta.atlas.github.mobile.page.MainScreen;
import io.qameta.atlas.github.mobile.page.SearchScreen;
import io.qameta.atlas.github.rules.UnZipResource;
import org.aeonbits.owner.ConfigFactory;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.allOf;
import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;
import static ru.yandex.qatools.matchers.webdriver.TextMatcher.text;

/**
 * Demo of simple tests for IOS and Android platform. Using atlas-mobile.
 */
public class WikipediaTest {

    private AppiumDriver driver;
    private Atlas atlas;
    private MobileConfig config = ConfigFactory.create(MobileConfig.class);
    private static final String NEXT = "Next";

    @ClassRule
    public static final UnZipResource unZipResource = new UnZipResource();

    @Before
    public void setUp() throws MalformedURLException {
        final String platform = config.platformName().trim();
        final URL url = new URL(config.url());
        final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if ("android".equalsIgnoreCase(platform)) {
            desiredCapabilities.setCapability("platformName", platform);
            desiredCapabilities.setCapability("deviceName", config.deviceName());
            desiredCapabilities.setCapability("platformVersion", config.platformVersion());
            desiredCapabilities.setCapability("appPackage", config.appPackage());
            desiredCapabilities.setCapability("appActivity", config.appActivity());
            desiredCapabilities.setCapability("unicodeKeyboard", config.unicodeKeyboard());
            desiredCapabilities.setCapability("resetKeyboard", config.resetKeyboard());
            desiredCapabilities.setCapability("automationName", config.automationName());
            desiredCapabilities.setCapability("newCommandTimeout", config.newCommandTimeout());
            desiredCapabilities.setCapability("app", config.apkFile());
            driver = new AndroidDriver(url, desiredCapabilities);
        } else if ("ios".equalsIgnoreCase(platform)) {
            desiredCapabilities.setCapability("platformName", platform);
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

    @Ignore
    @Test
    public void androidSimpleTest() {
        onMainScreen().searchWikipedia().click();
        onSearchScreen().search().sendKeys("Java");
    }

    @Ignore
    @Test
    public void iosSimpleTest() {
        onMainScreen().button(NEXT).click();
        onMainScreen().button(NEXT).click();
        onMainScreen().button(NEXT).click();
        onMainScreen().button("Get started").click();
        onMainScreen().searchWikipedia().click();
    }

    @Ignore
    @Test
    public void androidSwipeToDown() {
        onMainScreen().searchWikipedia().click();
        onSearchScreen().search().sendKeys("Atlas");
        onSearchScreen().item("Atlas LV-3B").swipeDownOn().click();
        onArticleScreen().articleTitle().should(allOf(displayed(), text("Atlas LV-3B")));
    }

    @Ignore
    @Test
    public void androidSwipeToUp() {
        onMainScreen().searchWikipedia().click();
        onSearchScreen().search().sendKeys("Java");
        onSearchScreen().item("JaVale McGee").swipeDownOn();
        onSearchScreen().item("Java (programming language)").swipeUpOn().click();
    }

    @After
    public void stopDriver() {
        this.driver.quit();
    }

    private ArticleScreen onArticleScreen() {
        return onPage(ArticleScreen.class);
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
