package io.qameta.atlas.api;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(ElementType.METHOD)
public @interface Retry {

    long timeout() default 5000L;

    long polling() default 250L;

    Class<? extends Throwable>[] ignoring() default {};

}
