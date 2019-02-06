package io.qameta.atlas.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * Method info auxiliary class.
 */
public class MethodInfo {

    private final Method method;
    private final Object[] args;

    public MethodInfo(final Method method, final Object... args) {
        this.method = method;
        this.args = cloneArgs(args);
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return cloneArgs(args);
    }

    public <T> Optional<T> getParameter(final Class<T> type) {
        final Parameter[] parameters = method.getParameters();
        T result = null;
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            if (parameter.getType().equals(type)) {
                result = type.cast(args[i]);
            }
        }
        return Optional.ofNullable(result);
    }

    public <T> Optional<T> getParameter(final Class<T> type, final Class<? extends Annotation> annotation) {
        final Parameter[] parameters = method.getParameters();
        T result = null;
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            if (parameter.getType().equals(type) && parameter.isAnnotationPresent(annotation)) {
                result = type.cast(args[i]);
            }
        }
        return Optional.ofNullable(result);
    }

    private Object[] cloneArgs(final Object... argsToClone) {
        return argsToClone == null ? null : argsToClone.clone();
    }

}
