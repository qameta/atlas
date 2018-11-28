package io.qameta.appium;

import io.appium.java_client.AppiumDriver;
import io.qameta.appium.context.AppiumDriverContext;
import io.qameta.appium.extension.*;
import io.qameta.core.internal.Configuration;
import io.qameta.core.internal.DefaultMethodExtension;
import io.qameta.webdriver.extension.ShouldMethodExtension;
import io.qameta.webdriver.extension.ToStringMethodExtension;


/**
 * Appium configuration.
 */
//CHECKSTYLE:OFF: ClassDataAbstractionCoupling
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
    }
}
//CHECKSTYLE:ON: ClassDataAbstractionCoupling
