package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import io.qameta.atlas.webdriver.context.URLBuilderContext;
import io.qameta.atlas.webdriver.internal.DefaultURLBuilder;
import io.qameta.atlas.webdriver.internal.URLBuilder;
import org.openqa.selenium.WrapsDriver;

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

    private static final String DEFAULT_PATH = "/";

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

        final String baseURI = Optional.ofNullable(System.getProperties().getProperty("ATLAS_WEBSITE_URL"))
                .orElseThrow(() -> new AtlasException("URI WebSite did'nt declared."));

        final Map<String, String> pathParameters = getPathSegmentValues(method, args);
        final String pathSegment = processTemplate(baseURL, pathParameters, "{", "}");

        final Map<String, String> queryParameters = Stream
                .of(getQueryValues(method, args), getQueryMapValues(method, args))
                .flatMap(map -> map.entrySet().stream())
                .collect(toMap(Entry::getKey, Entry::getValue));

        final URLBuilder urlBuilder = configuration.getContext(URLBuilderContext.class)
                .orElseGet(() -> new URLBuilderContext(new DefaultURLBuilder())).getValue();

        final String requestURL = urlBuilder.buildUrl(new Object[]{baseURI, pathSegment, queryParameters});

        Optional.of(requestURL)
                .filter(it -> !it.equals(baseURI + DEFAULT_PATH))
                .ifPresent(url -> wrapsDriver.getWrappedDriver().get(requestURL));

        return new Atlas(configuration).create(wrapsDriver.getWrappedDriver(), (Class<?>) method.getReturnType());
    }
}






