package io.qameta.atlas.api;

import io.qameta.atlas.util.MethodInfo;

/**
 * Method invoker.
 */
public interface MethodInvoker {

    Object invoke(Object proxy, MethodInfo methodInfo)
            throws Throwable;

}
