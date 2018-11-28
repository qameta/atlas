package io.qameta.webdriver.context;

import io.qameta.core.api.Context;
import io.qameta.core.internal.Configuration;
import org.openqa.selenium.WebDriver;

/**
 * WebDriver io.qameta.webdriver.context.
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
