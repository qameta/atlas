package io.qameta.atlas.webdriver.util;

import io.qameta.atlas.webdriver.extension.Param;
import io.qameta.atlas.webdriver.extension.Path;
import io.qameta.atlas.webdriver.extension.Query;
import io.qameta.atlas.webdriver.extension.QueryMap;
import org.hamcrest.Matcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static io.qameta.atlas.core.util.ReflectionUtils.isAnnotated;
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


    /**
     * Replace string template with value from {@link Param}.
     *
     * @param template   {@link String} - the string template.
     * @param parameters {@link Map} - the method's parameters.
     * @return - transformed value.
     *
     * Example:
     *  @FindBy("//div[{{ value }}]")
     *  AtlasWebElement childWithName(@Param("value") String value);
     *
     * where "//div[{{ value }}]" - the template, value - the method's parameter.
     *
     */
    public static String processParamTemplate(final String template, final Map<String, String> parameters) {
        return processTemplate(template, parameters, "{{ ", " }}");
    }


    /**
     * Replace string template with special value.
     *
     * @param template   {@link String}
     *                                 - the string template of.
     * @param parameters {@link Map}
     *                              - parameters.
     * @param prefix     {@link CharSequence}
     *                                       - the sequence of characters to be used at the beginning
     * @param suffix     {@link CharSequence}
     *                                       - the sequence of characters to be used at the end
     * @return - transformed value.
     */
    public static String processTemplate(final String template, final Map<String, String> parameters,
                                         final CharSequence prefix, final CharSequence suffix) {
        return parameters.entrySet()
                .stream()
                .reduce(template, (a, b) -> a.replace(prefix + b.getKey() + suffix, b.getValue()), (s, s2) -> s);
    }


    @SuppressWarnings({"PMD.UseVarargs", "unchecked"})
    public static Map<String, String> getQueryMapParameter(final Method method, final Object[] args) {
        final IntPredicate queryPredicate = index -> isAnnotated(method.getParameters()[index], QueryMap.class);

        return IntStream.range(0, method.getParameterCount())
                .filter(queryPredicate)
                .boxed()
                .map(index -> (HashMap<String, String>) args[index])
                .flatMap(x -> x.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @SuppressWarnings("PMD.UseVarargs")
    public static Map<String, String> getQueryParameters(final Method method, final Object[] args) {
        final IntPredicate queryPredicate = index -> isAnnotated(method.getParameters()[index], Query.class);
        return getParameters(queryPredicate, method,
                toMap(index -> getQueryValue(method.getParameters()[index]), index -> Objects.toString(args[index])));

    }

    @SuppressWarnings("PMD.UseVarargs")
    public static Map<String, String> getPathSegmentParameters(final Method method, final Object[] args) {
        final IntPredicate pathSegmentPredicate = index -> isAnnotated(method.getParameters()[index], Path.class);
        return getParameters(pathSegmentPredicate, method,
                toMap(index -> getPathValue(method.getParameters()[index]), index -> Objects.toString(args[index])));
    }

    /**
     * Get parameters name with annotation {@link Path} and all argument values.
     * @param method {@link Method} - the method on action.
     * @param args - method arguments.
     * @return {@link Map} return ParamName as Map Key, argument value as Map Value
     */
    @SuppressWarnings("PMD.UseVarargs")
    public static Map<String, String> getParamParameters(final Method method, final Object[] args) {
        final IntPredicate paramPredicate = index -> isAnnotated(method.getParameters()[index], Param.class);
        return getParameters(paramPredicate, method,
                toMap(index -> getParamValue(method.getParameters()[index]), index -> Objects.toString(args[index])));
    }


    /**
     * @param predicate {@link IntPredicate} - Predicate to find parameters with special annotation.
     * @param method    {@link Method} - action.
     * @param collector {@link Collector} - Map collector.
     * @return {@link Map} return ParamName as Map Key, argument value as Map Value
     */
    private static Map<String, String> getParameters(final IntPredicate predicate, final Method method,
                                                     final Collector<Integer, ?, Map<String, String>> collector) {
        return IntStream.range(0, method.getParameterCount())
                .filter(predicate)
                .boxed()
                .collect(collector);
    }


    private static String getParamValue(final AnnotatedElement element) {
        return element.getAnnotation(Param.class).value();
    }

    private static String getQueryValue(final AnnotatedElement element) {
        return element.getAnnotation(Query.class).value();
    }

    private static String getPathValue(final AnnotatedElement element) {
        return element.getAnnotation(Path.class).value();
    }


}
