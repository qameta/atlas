package io.qameta.atlas.core.api;

import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;

/**
 * Method invoker.
 */
public interface MethodInvoker {

    Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) throws Throwable;

}
