package io.qameta.atlas.util;

import java.lang.reflect.Method;

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

    private Object[] cloneArgs(final Object... argsToClone) {
        return argsToClone == null ? null : argsToClone.clone();
    }

}
