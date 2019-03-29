package io.qameta.atlas.core.util;

import io.qameta.atlas.core.AtlasException;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * Get matching methods for the given class, superclass and all of interfaces implemented by the given
     * class and its superclasses. Note: first, find method in current class and etc.
     *
     * @param cls            - current Class {@link Class}
     * @param methodName     - method name {@link String}
     * @param parameterTypes - parameter types {@link Class}
     * @return required method {@link Method}
     */
    public static Method getMatchingMethod(final Class<?> cls, final String methodName,
                                           final Class<?>... parameterTypes) {
        if (!Objects.nonNull(cls) && !Objects.requireNonNull(methodName).isEmpty()) {
            throw new AtlasException("Null class not allowed.");
        }
        final Predicate<Method> filter1 = method -> methodName.equals(method.getName())
                && Objects.deepEquals(parameterTypes, method.getParameterTypes());
        final Predicate<Method> filter2 = method -> methodName.equals(method.getName())
                && ClassUtils.isAssignable(parameterTypes, method.getParameterTypes(), true);

        return Stream.of(
                Collections.singletonList(cls), ClassUtils.getAllSuperclasses(cls), ClassUtils.getAllInterfaces(cls))
                .flatMap(Collection::stream)
                .map(Class::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .filter(filter1.or(filter2))
                .findFirst()
                .orElseThrow(() -> new AtlasException("Can't find valid method: " + cls.getName() + "." + methodName));
    }


    /**
     * Check is element have needful annotation.
     *
     * @param element        - all class implemented {@link AnnotatedElement} interface.
     * @param type {@link Class} - type of needful annotation
     * @return - true or false.
     */
    public static boolean isAnnotated(final AnnotatedElement element, final Class<? extends Annotation> type) {
        return element.isAnnotationPresent(type);
    }

}
