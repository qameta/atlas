package io.qameta.atlas.api;

import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;

/**
 * Listener.
 */
public interface Listener extends Extension {

    void beforeMethodCall(MethodInfo methodInfo, Configuration configuration);

    void afterMethodCall(MethodInfo methodInfo, Configuration configuration);

    void onMethodReturn(MethodInfo methodInfo, Configuration configuration, Object returned);

    void onMethodFailure(MethodInfo methodInfo, Configuration configuration, Throwable throwable);

}
