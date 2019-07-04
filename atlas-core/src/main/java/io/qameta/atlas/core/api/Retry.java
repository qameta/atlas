package io.qameta.atlas.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Retry configuration.
 */
@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(ElementType.METHOD)
public @interface Retry {

    long timeout() default -1;

    long polling() default -1;

    Class<? extends Throwable>[] ignoring() default {Throwable.class};

}
