package io.qameta.atlas.internal;

import io.qameta.atlas.api.Context;
import io.qameta.atlas.api.Extension;
import io.qameta.atlas.util.ReflectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Atlas configuration.
 */
public class Configuration {

    private final Set<Class<? extends Extension>> extensions;

    private final Set<Context> contexts;

    public Configuration() {
        this.extensions = new HashSet<>();
        this.contexts = new HashSet<>();
    }

    public void registerExtension(final Class<? extends Extension> extension) {
        this.extensions.add(extension);
    }

    public void addContext(final Context context) {
        this.contexts.add(context);
    }

    public <T extends Extension> List<T> getExtensions(final Class<T> type) {
        return extensions.stream()
                .filter(type::isAssignableFrom)
                .map(ReflectionUtils::newInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public <T> Optional<T> getContext(Class<T> contextType) {
        return contexts.stream()
                .filter(contextType::isInstance)
                .map(contextType::cast)
                .findFirst();
    }

    public <T> T requireContext(Class<T> contextType) {
        return getContext(contextType)
                .orElseThrow(() -> new ArithmeticException("Context not found by type " + contextType));
    }

}
