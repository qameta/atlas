package io.qameta.atlas.api;

import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;

/**
 * Method invoker.
 */
public interface MethodInvoker {

    Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) throws Throwable;

}
