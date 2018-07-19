package io.qameta.atlas.util;

import io.qameta.atlas.extension.Param;
import org.hamcrest.Matcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

/**
 * @author kurau (Yuri Kalinin)
 */
public final class MethodInfoUtils {

    private static final String UNEXPECTED_METHOD_SIGNATURE = "Unexpected method signature";

    private static final int ONLY_ONE_PARAM = 1;
    private static final int TWO_PARAMS = 2;

    private MethodInfoUtils() {
    }

    public static String getMessage(final Object... args) {
        if (args.length == ONLY_ONE_PARAM) {
            return "";
        } else if (args.length == TWO_PARAMS) {
            return (String) args[0];
        } else {
            throw new IllegalStateException(UNEXPECTED_METHOD_SIGNATURE);
        }
    }

    public static Matcher getMatcher(final Object... args) {
        if (args.length == ONLY_ONE_PARAM) {
            return (Matcher) args[0];
        } else if (args.length == TWO_PARAMS) {
            return (Matcher) args[1];
        } else {
            throw new IllegalStateException(UNEXPECTED_METHOD_SIGNATURE);
        }
    }

    public static String processTemplate(final String template, final Map<String, String> parameters) {
        return parameters.entrySet().stream()
                .reduce(template, (a, b) -> a.replace("{{ " + b.getKey() + " }}", b.getValue()), (s, s2) -> s);
    }

    @SuppressWarnings("PMD.UseVarargs")
    public static Map<String, String> getParameters(final Method method, final Object[] args) {
        return IntStream.range(0, method.getParameterCount())
                .filter(index -> hasParameterAnnotation(method.getParameters()[index]))
                .boxed()
                .collect(toMap(index -> getParameterName(method.getParameters()[index]),
                    index -> Objects.toString(args[index])));
    }

    public static String getParameterName(final AnnotatedElement element) {
        return element.getAnnotation(Param.class).value();
    }

    public static boolean hasParameterAnnotation(final AnnotatedElement element) {
        return element.isAnnotationPresent(Param.class);
    }
}
