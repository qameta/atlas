package io.qameta.atlas;

import org.hamcrest.Matcher;

import java.util.List;
import java.util.function.Predicate;

/**
 * Atlas Web Element Collection.
 * @param <E> the type of elements in this collection
 */
public interface ElementsCollection<E> extends List<E> {

    /**
     * This method handled by the {@link io.qameta.atlas.extensions.FilterCollectionExtension}.
     */
    ElementsCollection<E> filter(Predicate<E> predicate);

    /**
     * This method handled by the {@link io.qameta.atlas.extensions.ShouldExtension}.
     */
    ElementsCollection<E> should(Matcher matcher);

    /**
     * This method handled by the {@link io.qameta.atlas.extensions.ShouldExtension}.
     */
    ElementsCollection<E> should(String message, Matcher matcher);

    /**
     * This method handled by the {@link io.qameta.atlas.extensions.WaitUntilExtension}.
     */
    ElementsCollection<E> waitUntil(Matcher matcher);

    /**
     * This method handled by the {@link io.qameta.atlas.extensions.WaitUntilExtension}.
     */
    ElementsCollection<E> waitUntil(String message, Matcher matcher);
}
