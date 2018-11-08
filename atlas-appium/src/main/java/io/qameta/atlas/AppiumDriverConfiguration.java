package io.qameta.atlas;

import io.appium.java_client.AppiumDriver;
import io.qameta.atlas.context.AppiumDriverContext;
import io.qameta.atlas.extension.*;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.internal.DefaultMethodExtension;


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
        registerExtension(new SwipeToUpExtension());
        registerExtension(new SwipeToDownExtension());
        registerExtension(new SwipeToLeftExtension());
        registerExtension(new SwipeToRightExtension());
        registerExtension(new ShouldMethodExtension());
        registerExtension(new ToStringMethodExtension());
    }
}
