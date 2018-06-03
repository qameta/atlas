package io.qameta.atlas.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * Basic extension point.
 */
public interface MethodExtension extends Predicate<Method>, InvocationHandler {
}
