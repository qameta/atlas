package io.qameta.atlas.appium;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.appium.context.AppiumDriverContext;
import io.qameta.atlas.appium.extension.*;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.internal.DefaultMethodExtension;
import io.qameta.atlas.webdriver.extension.*;


/**
 * Appium configuration.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
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
        registerExtension(new SwipeUpOnExtension());
        registerExtension(new WaitUntilMethodExtension());
        registerExtension(new WrappedElementMethodExtension());
        registerExtension(new FilterCollectionExtension());
        registerExtension(new FindByCollectionExtension());
    }
}
