package io.qameta.webdriver;

import io.qameta.webdriver.context.WebDriverContext;
import io.qameta.webdriver.extension.DriverProviderExtension;
import io.qameta.webdriver.extension.FindByCollectionExtension;
import io.qameta.webdriver.extension.FindByExtension;
import io.qameta.webdriver.extension.ShouldMethodExtension;
import io.qameta.webdriver.extension.WaitUntilMethodExtension;
import io.qameta.core.internal.Configuration;
import io.qameta.core.internal.DefaultMethodExtension;
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
