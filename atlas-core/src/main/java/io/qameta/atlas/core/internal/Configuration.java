package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.api.Context;
import io.qameta.atlas.core.api.Extension;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Atlas configuration.
 */
public class Configuration {

    private final Map<Class<? extends Extension>, Extension> extensions;

    private final DefaultRetryer defaultRetryer;

    public Configuration() {
        this.extensions = new HashMap<>();
        this.defaultRetryer = new DefaultRetryer(5000L, 1000L, Collections.singletonList(Throwable.class));
    }

    public DefaultRetryer getDefaultRetryer() {
        return defaultRetryer;
    }

    public void registerExtension(final Extension extension) {
        this.extensions.put(extension.getClass(), extension);
    }

    public void registerContext(final Context context) {
        this.extensions.put(context.getClass(), context);
    }

    public void registerRetryer(final OptionalLong timeout, final OptionalLong polling) {
        timeout.ifPresent(defaultRetryer::timeoutInMillis);
        polling.ifPresent(defaultRetryer::polling);
    }

    public <T extends Extension> List<T> getExtensions(final Class<T> extensionType) {
        return extensions.values().stream()
                .filter(extensionType::isInstance)
                .map(extensionType::cast)
                .collect(Collectors.toList());
    }

    public <T> Optional<T> getContext(final Class<T> contextType) {
        return extensions.values().stream()
                .filter(contextType::isInstance)
                .map(contextType::cast)
                .findFirst();
    }

    public <T> T requireContext(final Class<T> contextType) {
        return getContext(contextType)
                .orElseThrow(() -> new ArithmeticException("Context not found by type " + contextType));
    }

    public Configuration child() {
        final Configuration configuration = new Configuration();
        this.getExtensions(Extension.class).forEach(configuration::registerExtension);
        return configuration;
    }


}
