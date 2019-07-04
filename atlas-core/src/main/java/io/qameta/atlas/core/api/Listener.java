package io.qameta.atlas.core.api;

import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;

/**
 * Listener.
 */
public interface Listener extends Extension {

    Configuration beforeMethodCall(MethodInfo methodInfo, Configuration configuration);

    void afterMethodCall(MethodInfo methodInfo, Configuration configuration);

    void onMethodReturn(MethodInfo methodInfo, Configuration configuration, Object returned);

    void onMethodFailure(MethodInfo methodInfo, Configuration configuration, Throwable throwable);

}
