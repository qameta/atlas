package io.qameta.atlas;

import java.lang.reflect.Method;

/**
 * Listener.
 */
public interface Listener {

    void beforeMethodCall(Method method, Object... args);

    void afterMethodCall(Method method, Object... args);

    void onMethodReturn(Method method, Object[] args, Object returned);

    void onMethodFailure(Method method, Object[] args, Throwable throwable);

}
