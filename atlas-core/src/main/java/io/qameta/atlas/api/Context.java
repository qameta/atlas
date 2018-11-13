package io.qameta.atlas.api;

/**
 * Main context.
 * @param <T> returned value.
 */
@FunctionalInterface
public interface Context<T> extends Extension {

    /**
     * Returns the context value.
     *
     * @return the context value.
     */
    T getValue();

}
