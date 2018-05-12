package io.qameta.atlas.exception;

import org.openqa.selenium.WebDriverException;

/**
 * @author kurau (Yuri Kalinin)
 */
public class WaitUntilException extends WebDriverException {

    public WaitUntilException(final String message) {
        super(message);
    }

}
