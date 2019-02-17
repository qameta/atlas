package io.qameta.atlas.core.internal;

import io.qameta.atlas.core.api.Context;
import io.qameta.atlas.core.api.Extension;
import io.qameta.atlas.core.api.MethodInvoker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Atlas configuration.
 */
public class Configuration {

    private final Map<Class<? extends Extension>, Extension> extensions;

    private MethodInvoker defaultInvoker = new TargetMethodInvoker();

    public Configuration() {
        this.extensions = new HashMap<>();
    }

    public void registerExtension(final Extension extension) {
        this.extensions.put(extension.getClass(), extension);
    }

    public void registerContext(final Context context) {
        this.extensions.put(context.getClass(), context);
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

    public MethodInvoker getDefaultInvoker() {
        return defaultInvoker;
    }

    public void setDefaultInvoker(final MethodInvoker defaultInvoker) {
        this.defaultInvoker = defaultInvoker;
    }

    public Configuration child() {
        final Configuration configuration = new Configuration();
        this.getExtensions(Extension.class).forEach(configuration::registerExtension);
        configuration.setDefaultInvoker(this.getDefaultInvoker());
        return configuration;
    }


}
