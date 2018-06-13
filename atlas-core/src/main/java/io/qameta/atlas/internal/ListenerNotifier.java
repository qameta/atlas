package io.qameta.atlas.internal;

import io.qameta.atlas.api.Listener;
import io.qameta.atlas.util.MethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void beforeMethodCall(final MethodInfo methodInfo) {
        for (Listener listener : listeners) {
            try {
                listener.beforeMethodCall(methodInfo);
            } catch (Exception e) {
                LOGGER.error("Error during listener {} beforeMethodCall", listener, e);
            }
        }
    }

    @Override
    public void afterMethodCall(final MethodInfo methodInfo) {
        for (Listener listener : listeners) {
            try {
                listener.afterMethodCall(methodInfo);
            } catch (Exception e) {
                LOGGER.error("Error during listener {} afterMethodCall", listener, e);
            }
        }
    }

    @Override
    public void onMethodReturn(final MethodInfo methodInfo, final Object returned) {
        for (Listener listener : listeners) {
            try {
                listener.onMethodReturn(methodInfo, returned);
            } catch (Exception e) {
                LOGGER.error("Error during listener {} onMethodReturn", listener, e);
            }
        }
    }

    @Override
    public void onMethodFailure(final MethodInfo methodInfo, final Throwable throwable) {
        for (Listener listener : listeners) {
            try {
                listener.onMethodFailure(methodInfo, throwable);
            } catch (Exception e) {
                LOGGER.error("Error during listener {} onMethodFailure", listener, e);
            }
        }
    }

}
