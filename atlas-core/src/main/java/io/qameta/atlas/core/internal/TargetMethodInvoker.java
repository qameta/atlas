package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.api.MethodInvoker;
import io.qameta.atlas.core.context.TargetContext;
import io.qameta.atlas.core.util.MethodInfo;
import io.qameta.atlas.core.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Target method invoker.
 */
public class TargetMethodInvoker implements MethodInvoker {

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration config) throws Throwable {
        final Object target = config.requireContext(TargetContext.class).getValue().instance();
        final Method targetMethod = ReflectionUtils.getMatchingMethod(
                target.getClass(), methodInfo.getMethod().getName(), getParametersTypes(methodInfo.getArgs()));
        try {
            targetMethod.setAccessible(true);
            return targetMethod.invoke(target, methodInfo.getArgs());
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    private Class<?>[] getParametersTypes(final Object... args) {
        if (args == null) {
            return new Class<?>[]{};
        }
        return Arrays.stream(args)
                .map(Object::getClass).toArray(Class[]::new);
    }
}
