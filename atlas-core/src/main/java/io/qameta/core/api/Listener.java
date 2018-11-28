package io.qameta.core.api;

import io.qameta.core.internal.Configuration;
import io.qameta.core.util.MethodInfo;

/**
 * Listener.
 */
public interface Listener extends Extension {

    void beforeMethodCall(MethodInfo methodInfo, Configuration configuration);

    void afterMethodCall(MethodInfo methodInfo, Configuration configuration);

    void onMethodReturn(MethodInfo methodInfo, Configuration configuration, Object returned);

    void onMethodFailure(MethodInfo methodInfo, Configuration configuration, Throwable throwable);

}
