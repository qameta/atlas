package io.qameta.atlas.appium;

//CHECKSTYLE:OFF
import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.appium.context.AppiumDriverContext;
import io.qameta.atlas.appium.extension.*;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.internal.DefaultMethodExtension;
import io.qameta.atlas.webdriver.extension.ShouldMethodExtension;
import io.qameta.atlas.webdriver.extension.ToStringMethodExtension;


/**
 * Appium configuration.
 */
public class AppiumDriverConfiguration extends Configuration {

    public AppiumDriverConfiguration(final AppiumDriver appiumDriver) {
        registerContext(new AppiumDriverContext(appiumDriver));
        registerExtension(new AppiumDriverProviderExtension());
        registerExtension(new DefaultMethodExtension());
        registerExtension(new AppiumFindByExtension());
        registerExtension(new ToStringMethodExtension());
        registerExtension(new LongPressExtension());
        registerExtension(new SwipeDownOnExtension());
        registerExtension(new ShouldMethodExtension());
        registerExtension(new ToStringMethodExtension());
        registerExtension(new SwipeUpOnExtension());
    }
}
//CHECKSTYLE:ON
