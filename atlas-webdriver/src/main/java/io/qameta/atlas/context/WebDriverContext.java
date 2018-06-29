package io.qameta.atlas.context;

import io.qameta.atlas.api.Context;
import io.qameta.atlas.internal.Configuration;
import org.openqa.selenium.WebDriver;

/**
 * WebDriver context.
 * Used to save WebDriver instance to {@link Configuration}.
 */
public class WebDriverContext implements Context<WebDriver> {

    private final WebDriver webDriver;

    public WebDriverContext(final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public WebDriver getValue() {
        return webDriver;
    }
}
