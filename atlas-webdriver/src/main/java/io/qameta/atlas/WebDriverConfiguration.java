package io.qameta.atlas;

import io.qameta.atlas.context.WebDriverContext;
import io.qameta.atlas.extension.DriverProviderExtension;
import io.qameta.atlas.extension.FindByCollectionExtension;
import io.qameta.atlas.extension.FindByExtension;
import io.qameta.atlas.extension.ShouldMethodExtension;
import io.qameta.atlas.extension.WaitUntilMethodExtension;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.internal.DefaultMethodExtension;
import org.openqa.selenium.WebDriver;

/**
 * WebDriver configuration.
 */
public class WebDriverConfiguration extends Configuration {

    public WebDriverConfiguration(final WebDriver webDriver) {
        registerContext(new WebDriverContext(webDriver));
        registerExtension(new DriverProviderExtension());
        registerExtension(new DefaultMethodExtension());
        registerExtension(new FindByExtension());
        registerExtension(new FindByCollectionExtension());
        registerExtension(new ShouldMethodExtension());
        registerExtension(new WaitUntilMethodExtension());
    }

}
