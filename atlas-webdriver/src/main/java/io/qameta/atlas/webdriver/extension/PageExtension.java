package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import io.qameta.atlas.webdriver.util.http.url.HttpUrl;
import org.openqa.selenium.internal.WrapsDriver;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import static io.qameta.atlas.core.util.ReflectionUtils.isAnnotated;
import static io.qameta.atlas.webdriver.util.MethodInfoUtils.*;
import static java.util.stream.Collectors.toMap;


/**
 * Extension for methods with {@link Page} annotation.
 */
public class PageExtension implements MethodExtension {

    private static final String SLASH = "/";

    @Override
    public boolean test(final Method method) {
        return isAnnotated(method, Page.class);
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        assert proxy instanceof WrapsDriver;


        final Method method = methodInfo.getMethod();
        final Object[] args = methodInfo.getArgs();

        final String baseURL = method.getAnnotation(Page.class).url();
        final WrapsDriver wrapsDriver = (WrapsDriver) proxy;

        if (!SLASH.equals(baseURL)) {
            final String baseURI = Optional.ofNullable(System.getProperties().getProperty("ATLAS_WEBSITE_URL"))
                    .orElseThrow(() -> new AtlasException("URI WebSite did'nt declared."));

            final Map<String, String> pathParameters = getPathSegmentParameters(method, args);
            final String pathSegment = processTemplate(baseURL, pathParameters, "{", "}");

            final Map<String, String> queryParameters = Stream
                    .of(getQueryParameters(method, args), getQueryMapParameter(method, args))
                    .flatMap(map -> map.entrySet().stream())
                    .collect(toMap(Entry::getKey, Entry::getValue));
            final String requestURL = buildUrl(baseURI, pathSegment, queryParameters);

            wrapsDriver.getWrappedDriver().get(requestURL);
        }

        return new Atlas(configuration).create(wrapsDriver.getWrappedDriver(), (Class<?>) method.getReturnType());
    }

    /**
     * Build full completed URL.
     *
     * @param baseURI         {@link String} - the base URI of WebSite.
     * @param pathSegment     {@link String} - the path segment.
     * @param queryParameters {@link Map} - the query parameters.
     * @return {@link String}
     */
    private String buildUrl(final String baseURI, final String pathSegment, final Map<String, String> queryParameters) {
        final HttpUrl.Builder urlBuilder = HttpUrl.get(baseURI).newBuilder();
        urlBuilder.addPathSegments(pathSegment);
        queryParameters.forEach(urlBuilder::addQueryParameter);
        return urlBuilder.toString();
    }
}





