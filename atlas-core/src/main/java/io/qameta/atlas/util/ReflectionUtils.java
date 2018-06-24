package io.qameta.atlas.util;

import io.qameta.atlas.AtlasException;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reflection utils.
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    private static List<Class<?>> getAllInterfaces(final Class<?>... classes) {
        final List<Class<?>> result = new ArrayList<>();
        Arrays.stream(classes).forEach(clazz -> {
            result.addAll(ClassUtils.getAllInterfaces(clazz));
            result.add(clazz);
        });
        return result;
    }

    public static List<Method> getMethods(final Class<?>... clazz) {

        return getAllInterfaces(clazz).stream()
                .flatMap(m -> Arrays.stream(m.getDeclaredMethods()))
                .collect(Collectors.toList());
    }

    public static <T> T newInstance(final Class<T> clazz) {
        try {
            return ConstructorUtils.invokeConstructor(clazz);
        } catch (Exception e) {
            throw new AtlasException("Can't instantiate class " + clazz, e);
        }
    }

}
