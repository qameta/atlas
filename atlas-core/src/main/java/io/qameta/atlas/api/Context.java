package io.qameta.atlas.api;

/**
 * Main context
 * @param <T> returned value.
 */
@FunctionalInterface
public interface Context<T> extends Extension {

    /**
     * Returns the io.qameta.atlas.context value.
     *
     * @return the io.qameta.atlas.context value.
     */
    T getValue();

}
