package io.qameta.atlas.internal;

import io.qameta.atlas.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Listener notifier.
 */
public class ListenerNotifier implements Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerNotifier.class);

    private final List<Listener> listeners = new ArrayList<>();

    public void addListeners(final Listener... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
    }

    @Override
    public void beforeMethodCall(final Method method, final Object[] args) {
        for (Listener listener: listeners) {
            try {
                listener.beforeMethodCall(method, args);
            } catch (Exception e) {
                LOGGER.error("Error during listener {} beforeMethodCall", listener, e);
            }
        }
    }

    @Override
    public void afterMethodCall(final Method method, final Object[] args) {
        for (Listener listener: listeners) {
            try {
                listener.afterMethodCall(method, args);
            } catch (Exception e) {
                LOGGER.error("Error during listener {} afterMethodCall", listener, e);
            }
        }
    }

    @Override
    public void onMethodReturn(final Method method, final Object[] args, final Object returned) {
        for (Listener listener: listeners) {
            try {
                listener.onMethodReturn(method, args, returned);
            } catch (Exception e) {
                LOGGER.error("Error during listener {} onMethodReturn", listener, e);
            }
        }
    }

    @Override
    public void onMethodFailure(final Method method, final Object[] args, final Throwable throwable) {
        for (Listener listener: listeners) {
            try {
                listener.onMethodFailure(method, args, throwable);
            } catch (Exception e) {
                LOGGER.error("Error during listener {} onMethodFailure", listener, e);
            }
        }
    }

}
