package io.qameta.atlas.internal;

import io.qameta.atlas.api.MethodInvoker;
import io.qameta.atlas.context.TargetContext;
import io.qameta.atlas.util.MethodInfo;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Target method invoker.
 */
public class TargetMethodInvoker implements MethodInvoker {

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration config) throws Throwable {
        final Object target = config.requireContext(TargetContext.class).get();

        final Method targetMethod = MethodUtils.getMatchingAccessibleMethod(
                target.getClass(), methodInfo.getMethod().getName(), getParametersTypes(methodInfo.getArgs()));
        try {
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
                .map(Object::getClass)
                .collect(Collectors.toList()).toArray(new Class<?>[]{});
    }

}
