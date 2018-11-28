package io.qameta.core.api;

import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * Basic extension point.
 */
public interface MethodExtension extends Predicate<Method>, MethodInvoker, Extension {
}
