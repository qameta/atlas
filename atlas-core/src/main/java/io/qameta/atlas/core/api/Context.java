package io.qameta.atlas.core.api;

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
