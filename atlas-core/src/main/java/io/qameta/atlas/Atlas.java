package io.qameta.atlas;

import io.qameta.atlas.api.Context;
import io.qameta.atlas.api.Listener;
import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.api.MethodInvoker;
import io.qameta.atlas.context.TargetContext;
import io.qameta.atlas.internal.AtlasMethodHandler;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.internal.ListenerNotifier;
import io.qameta.atlas.internal.TargetMethodInvoker;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.qameta.atlas.util.ReflectionUtils.getMethods;

/**
 * @author Artem Eroshenko.
 */
public class Atlas {

    private final Configuration configuration;

    public Atlas() {
        this(new Configuration());
    }

    public Atlas(final Configuration configuration) {
        this.configuration = configuration;
    }

    public Atlas listener(final Listener listener) {
        this.configuration.registerExtension(listener);
        return this;
    }

    public Atlas extension(final MethodExtension methodExtension) {
        this.configuration.registerExtension(methodExtension);
        return this;
    }

    public Atlas context(final Context context) {
        this.configuration.registerContext(context);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Object target, final Class<T> type) {
        final Map<Method, MethodInvoker> invokers = new HashMap<>();
        final List<Method> methods = getMethods(type, Object.class);
        this.context(new TargetContext(target));

        methods.forEach(method -> {
            final MethodInvoker invoker = configuration.getExtensions(MethodExtension.class).stream()
                    .filter(extension -> extension.test(method)).map(MethodInvoker.class::cast).findFirst()
                    .orElse(new TargetMethodInvoker());
            invokers.put(method, invoker);
        });

        final ListenerNotifier notifier = new ListenerNotifier();
        configuration.getExtensions(Listener.class).forEach(notifier::addListeners);

        return (T) Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[]{type},
                new AtlasMethodHandler(configuration, notifier, invokers)
        );
    }

}
