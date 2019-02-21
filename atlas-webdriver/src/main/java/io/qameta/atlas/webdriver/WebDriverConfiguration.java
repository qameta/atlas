package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.internal.DefaultRetryer;
import io.qameta.atlas.webdriver.context.WebDriverContext;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.internal.DefaultMethodExtension;
import io.qameta.atlas.webdriver.extension.DriverProviderExtension;
import io.qameta.atlas.webdriver.extension.ExecuteJScriptMethodExtension;
import io.qameta.atlas.webdriver.extension.FindByCollectionExtension;
import io.qameta.atlas.webdriver.extension.FindByExtension;
import io.qameta.atlas.webdriver.extension.PageExtension;
import io.qameta.atlas.webdriver.extension.ShouldMethodExtension;
import io.qameta.atlas.webdriver.extension.WaitUntilMethodExtension;
import io.qameta.atlas.webdriver.extension.WrappedElementMethodExtension;
import org.openqa.selenium.WebDriver;

import java.util.Collections;

/**
 * WebDriver configuration.
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
public class WebDriverConfiguration extends Configuration {

    public WebDriverConfiguration(final WebDriver webDriver) {
        registerContext(new WebDriverContext(webDriver));
        registerExtension(new DriverProviderExtension());
        registerExtension(new DefaultMethodExtension());
        registerExtension(new FindByExtension());
        registerExtension(new FindByCollectionExtension());
        registerExtension(new ShouldMethodExtension());
        registerExtension(new WaitUntilMethodExtension());
        registerExtension(new WrappedElementMethodExtension());
        registerExtension(new ExecuteJScriptMethodExtension());
        registerExtension(new PageExtension());
        registerContext(new DefaultRetryer(5000L, 1000L, Collections.singletonList(Throwable.class)));
    }

    public WebDriverConfiguration(final WebDriver webDriver, final String baseUrl) {
        this(webDriver);
        System.getProperties().setProperty("ATLAS_WEBSITE_URL", baseUrl);
    }
}
