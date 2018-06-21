package io.qameta.atlas.api;

@FunctionalInterface
public interface Context<T> extends Extension {

    /**
     * Returns the context value.
     *
     * @return the context value.
     */
    T get();

}
