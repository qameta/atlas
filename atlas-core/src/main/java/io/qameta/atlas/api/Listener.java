package io.qameta.atlas.api;

import io.qameta.atlas.util.MethodInfo;

/**
 * Listener.
 */
public interface Listener extends Extension {

    void beforeMethodCall(MethodInfo methodInfo);

    void afterMethodCall(MethodInfo methodInfo);

    void onMethodReturn(MethodInfo methodInfo, Object returned);

    void onMethodFailure(MethodInfo methodInfo, Throwable throwable);

}
