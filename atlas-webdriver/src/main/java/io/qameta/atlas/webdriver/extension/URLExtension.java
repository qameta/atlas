package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import okhttp3.HttpUrl;
import org.openqa.selenium.internal.WrapsDriver;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Extension for methods with {@link URL} annotation.
 */
public class URLExtension implements MethodExtension {

    private static final String STORE_URI = "WEBSITE_URL";

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(URL.class);
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        assert proxy instanceof WrapsDriver;

        final Method method = methodInfo.getMethod();

        Optional.ofNullable(method.getDeclaringClass().getAnnotation(BaseURI.class)).ifPresent(url ->
                configuration.getStore().putIfAbsent(STORE_URI, url.value()));

        final String baseURI = (String) Optional.ofNullable(configuration.getStore().get(STORE_URI))
                .orElseThrow(() -> new AtlasException("Can't find valid URI WebSite"));

        final URIHandler queryHandler = new URIHandler(baseURI);

        final Map<String, String> pathParameters = queryHandler.getPathParameters(method, methodInfo.getArgs());
        final String pathSegment = queryHandler
                .processPathTemplate(method.getAnnotation(URL.class).value(), pathParameters);

        final Map<String, String> queryParameters = Stream.of(
                queryHandler.getQueryParameters(method, methodInfo.getArgs()),
                queryHandler.getQueryMapParameter(method, methodInfo.getArgs()))
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        final String requestURL = queryHandler.buildURI(pathSegment, queryParameters);
        final WrapsDriver wrapsDriver = (WrapsDriver) proxy;
        wrapsDriver.getWrappedDriver().get(requestURL);
        return new Atlas(configuration).create(wrapsDriver.getWrappedDriver(), (Class<?>) method.getReturnType());
    }

    /**
     * URI Handler.
     */
    private static final class URIHandler {

        private final HttpUrl.Builder urlBuilder;

        URIHandler(final String url) {
            urlBuilder = HttpUrl.get(url).newBuilder();
        }

        private boolean hasQueryAnnotation(final AnnotatedElement element) {
            return element.isAnnotationPresent(Query.class);
        }

        private boolean hasQueryMapAnnotation(final AnnotatedElement element) {
            return element.isAnnotationPresent(QueryMap.class);
        }

        private boolean hasPathAnnotation(final AnnotatedElement element) {
            return element.isAnnotationPresent(Path.class);
        }

        private String getQueryValue(final AnnotatedElement element) {
            return element.getAnnotation(Query.class).value();
        }

        private String getPathValue(final AnnotatedElement element) {
            return element.getAnnotation(Path.class).value();
        }

        @SuppressWarnings({"PMD.UseVarargs", "unchecked"})
        private Map<String, String> getQueryMapParameter(final Method method, final Object[] args) {
            return IntStream.range(0, method.getParameterCount())
                    .filter(index -> hasQueryMapAnnotation(method.getParameters()[index]))
                    .boxed()
                    .map(index -> (HashMap<String, String>) (args[index]))
                    .filter(it -> !it.isEmpty()).findFirst().orElseGet(HashMap::new);
        }

        //CHECKSTYLE:OFF
        @SuppressWarnings("PMD.UseVarargs")
        private Map<String, String> getQueryParameters(final Method method, final Object[] args) {
            return IntStream.range(0, method.getParameterCount())
                    .filter(index -> hasQueryAnnotation(method.getParameters()[index]))
                    .boxed().collect(toMap(index -> getQueryValue(method.getParameters()[index]),
                            index -> Objects.toString(args[index])));
        }

        @SuppressWarnings("PMD.UseVarargs")
        private Map<String, String> getPathParameters(final Method method, final Object[] args) {
            return IntStream.range(0, method.getParameterCount())
                    .filter(index -> hasPathAnnotation(method.getParameters()[index]))
                    .boxed().collect(toMap(index -> getPathValue(method.getParameters()[index]),
                            index -> Objects.toString(args[index])));
        }
        //CHECKSTYLE:ON

        private String buildURI(final String pathSegment, final Map<String, String> parameters) {
            urlBuilder.addPathSegments(pathSegment);
            parameters.forEach(urlBuilder::addQueryParameter);
            return urlBuilder.toString();
        }

        private String processPathTemplate(final String template, final Map<String, String> path) {
            return path.entrySet().stream()
                    .reduce(template, (a, b) -> a.replace("{" + b.getKey() + "}", b.getValue()), (s, s2) -> s);
        }
    }

}






