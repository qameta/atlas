package io.qameta.core;

/**
 * @author ero
 */
public class AtlasException extends RuntimeException {

    public AtlasException(final String message) {
        super(message);
    }

    public AtlasException(final String message, final Exception exception) {
        super(message, exception);
    }

}
