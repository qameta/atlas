package io.qameta.atlas.extension;

import io.qameta.atlas.Atlas;
import io.qameta.atlas.ElementsCollection;
import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Filter method extension for {@link io.qameta.atlas.ElementsCollection}.
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
        return new Atlas()
                .create(((List) proxy).stream().filter(condition).collect(toList()), ElementsCollection.class);
    }
}
