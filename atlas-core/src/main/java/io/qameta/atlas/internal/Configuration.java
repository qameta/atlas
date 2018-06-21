package io.qameta.atlas.internal;

import io.qameta.atlas.api.Extension;
import io.qameta.atlas.util.ReflectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Atlas configuration.
 */
public class Configuration {

    private final Set<Class<? extends Extension>> extensions;

    public Configuration() {
        this.extensions = new HashSet<>();
    }

    public void register(final Class<? extends Extension> extension) {
        this.extensions.add(extension);
    }

    public <T extends Extension> List<T> getExtensions(final Class<T> type) {
        return extensions.stream()
                .filter(type::isAssignableFrom)
                .map(ReflectionUtils::newInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

}
