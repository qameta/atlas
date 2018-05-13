package io.qameta.atlas.exception;

import org.openqa.selenium.WebDriverException;

/**
 * Exception for custom behavior of the {@link io.qameta.atlas.extensions.WaitUntilExtension}.
 */
public class WaitUntilException extends WebDriverException {

    public WaitUntilException(final String message) {
        super(message);
    }

}
