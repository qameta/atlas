package io.qameta.atlas.webdriver.exception;

import io.qameta.atlas.webdriver.extension.WaitUntilMethodExtension;
import org.openqa.selenium.WebDriverException;

/**
 * Exception for custom behavior of the {@link WaitUntilMethodExtension}.
 */
public class WaitUntilException extends WebDriverException {

    public WaitUntilException(final String message) {
        super(message);
    }

}
