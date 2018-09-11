package io.qameta.atlas.api;

import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;

/**
 * Listener.
 */
public interface Listener extends Extension {

    default void beforeMethodCall(MethodInfo methodInfo, Configuration configuration) {

    }

    default void afterMethodCall(MethodInfo methodInfo, Configuration configuration) {

    }

    default void onMethodReturn(MethodInfo methodInfo, Configuration configuration, Object returned) {

    }

    default void onMethodFailure(MethodInfo methodInfo, Configuration configuration, Throwable throwable) {

    }

}
