package io.qameta.atlas.core;

import io.qameta.atlas.core.api.Listener;
import io.qameta.atlas.core.internal.DefaultRetryer;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ListenerTest {

    @Test
    public void shouldFireListenerMethods() {
        SimpleSayHello hello = mock(SimpleSayHello.class);
        Listener listener = mock(Listener.class);
        SimpleSayHello proxyHello = new Atlas()
                .context(new DefaultRetryer(5000L, 1000L, Collections.singletonList(Throwable.class)))
                .listener(listener)
                .create(hello, SimpleSayHello.class);

        proxyHello.hello();

        verify(listener, times(1)).beforeMethodCall(any(), any());
        verify(listener, times(1)).afterMethodCall(any(), any());
        verify(listener, times(1)).onMethodReturn(any(), any(), any());
        verify(listener, times(0)).onMethodFailure(any(), any(), any());

    }

    public interface SimpleSayHello {

        default void hello() {

        }

    }

}
