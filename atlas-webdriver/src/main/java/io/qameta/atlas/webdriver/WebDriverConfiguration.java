package io.qameta.atlas.webdriver;

import io.qameta.atlas.webdriver.context.WebDriverContext;
import io.qameta.atlas.webdriver.extension.*;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.internal.DefaultMethodExtension;
import org.openqa.selenium.WebDriver;

/**
 * WebDriver configuration.
 */
//CHECKSTYLE:OFF: ClassDataAbstractionCoupling
public class WebDriverConfiguration extends Configuration {

    public WebDriverConfiguration(final WebDriver webDriver) {
        registerContext(new WebDriverContext(webDriver));
        registerExtension(new DriverProviderExtension());
        registerExtension(new DefaultMethodExtension());
        registerExtension(new FindByExtension());
        registerExtension(new FindByCollectionExtension());
        registerExtension(new ShouldMethodExtension());
        registerExtension(new WaitUntilMethodExtension());
        registerExtension(new URLExtension());
        registerExtension(new PageExtension());
        registerExtension(new DefaultSiteExtension());
    }
}
//CHECKSTYLE:ON: ClassDataAbstractionCoupling
