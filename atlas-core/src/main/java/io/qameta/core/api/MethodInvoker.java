package io.qameta.core.api;

import io.qameta.core.internal.Configuration;
import io.qameta.core.util.MethodInfo;

/**
 * Method invoker.
 */
public interface MethodInvoker {

    Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) throws Throwable;

}
