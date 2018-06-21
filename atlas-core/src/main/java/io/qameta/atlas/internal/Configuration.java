package io.qameta.atlas.internal;

import io.qameta.atlas.api.Context;
import io.qameta.atlas.api.Extension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Atlas configuration.
 */
public class Configuration {

    private final Set<Extension> extensions;

    public Configuration() {
        this.extensions = new HashSet<>();
    }

    public void registerExtension(final Extension extension) {
        this.extensions.add(extension);
    }

    public void registerContext(final Context context) {
        this.extensions.add(context);
    }

    public <T extends Extension> List<T> getExtensions(final Class<T> extensionType) {
        return extensions.stream()
                .filter(extensionType::isInstance)
                .map(extensionType::cast)
                .collect(Collectors.toList());
    }

    public <T> Optional<T> getContext(Class<T> contextType) {
        return extensions.stream()
                .filter(contextType::isInstance)
                .map(contextType::cast)
                .findFirst();
    }

    public <T> T requireContext(Class<T> contextType) {
        return getContext(contextType)
                .orElseThrow(() -> new ArithmeticException("Context not found by type " + contextType));
    }

}
