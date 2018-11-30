package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.api.MethodInvoker;
import io.qameta.atlas.core.context.TargetContext;
import io.qameta.atlas.core.util.MethodInfo;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Target method invoker.
 */
public class TargetMethodInvoker implements MethodInvoker {

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration config) throws Throwable {
        final Object target = config.requireContext(TargetContext.class).getValue().instance();

        final Optional<Method> targetMethod = Optional.ofNullable(MethodUtils.getMatchingMethod(
                target.getClass(), methodInfo.getMethod().getName(), getParametersTypes(methodInfo.getArgs())));
        try {
            return targetMethod.orElseGet(() -> MethodUtils.getAccessibleMethod(
                    target.getClass(), methodInfo.getMethod().getName(), getParametersTypes(methodInfo.getArgs())))
                    .invoke(target, methodInfo.getArgs());
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    private Class<?>[] getParametersTypes(final Object... args) {
        if (args == null) {
            return new Class<?>[]{};
        }
        return Arrays.stream(args)
                .map(Object::getClass)
                .collect(Collectors.toList()).toArray(new Class<?>[]{});
    }
}
