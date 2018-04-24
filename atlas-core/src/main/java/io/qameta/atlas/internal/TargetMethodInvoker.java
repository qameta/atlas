package io.qameta.atlas.internal;

import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Listener notifier.
 */
public class TargetMethodInvoker implements InvocationHandler {

    private final Supplier<?> targetSupplier;

    public TargetMethodInvoker(final Supplier<?> targetSupplier) {
        this.targetSupplier = targetSupplier;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final Object target = targetSupplier.get();

        final Method targetMethod = MethodUtils.getMatchingAccessibleMethod(
                target.getClass(), method.getName(), getParametersTypes(args));
        try {
            return targetMethod.invoke(target, args);
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
