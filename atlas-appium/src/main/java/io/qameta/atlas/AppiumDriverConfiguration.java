package io.qameta.atlas;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.context.AppiumDriverContext;
import io.qameta.atlas.extension.*;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.internal.DefaultMethodExtension;


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
        registerExtension(new SwipeUpOnExtension());
        registerExtension(new ShouldMethodExtension());
        registerExtension(new ToStringMethodExtension());
    }
}
//CHECKSTYLE:ON: ClassDataAbstractionCoupling
