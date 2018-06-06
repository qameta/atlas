package io.qameta.atlas;

import io.qameta.atlas.api.Listener;
import org.junit.Test;

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
                .listener(listener)
                .create(hello, SimpleSayHello.class);

        proxyHello.hello();

        verify(listener, times(1)).beforeMethodCall(any());
        verify(listener, times(1)).afterMethodCall(any());
        verify(listener, times(1)).onMethodReturn(any(), any());
        verify(listener, times(0)).onMethodFailure(any(), any());

    }

    public interface SimpleSayHello {

        default void hello() {

        }

    }

}
