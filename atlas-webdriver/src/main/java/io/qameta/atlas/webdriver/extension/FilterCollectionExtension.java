package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.ElementsCollection;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Filter method extension for {@link io.qameta.atlas.webdriver.ElementsCollection}.
 */
public class FilterCollectionExtension implements MethodExtension {

    private static final String FILTER = "filter";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(FILTER) && List.class.isAssignableFrom(method.getDeclaringClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ElementsCollection invoke(final Object proxy,
                                     final MethodInfo methodInfo,
                                     final Configuration configuration) {
        final Predicate condition = (Predicate) methodInfo.getArgs()[0];
        return new Atlas(configuration)
                .create(((List) proxy).stream().filter(condition).collect(toList()), ElementsCollection.class);
    }
}
