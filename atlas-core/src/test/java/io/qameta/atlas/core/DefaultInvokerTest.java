package io.qameta.atlas.core;

import org.junit.Before;
import org.junit.Test;

import io.qameta.atlas.core.api.MethodInvoker;
import io.qameta.atlas.core.internal.Configuration;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DefaultInvokerTest {

    private MethodInvoker defaultInvoker;
    private Configuration configuration;

    @Before
    public void setUp() {
        defaultInvoker = mock(MethodInvoker.class);
        configuration = new Configuration();
        configuration.setDefaultInvoker(defaultInvoker);
    }

    @Test
    public void shouldPassGivenInvokerToChildConfig() throws Throwable {
        new Atlas(configuration.child())
                .create("", CharSequence.class)
                .subSequence(0, 0);

        verify(defaultInvoker, times(1)).invoke(any(), any(), any());
    }

}
