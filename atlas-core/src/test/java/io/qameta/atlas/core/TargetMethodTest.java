package io.qameta.atlas.core;

import io.qameta.atlas.core.internal.DefaultRetryer;
import io.qameta.atlas.core.testdata.CustomException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TargetMethodTest {

    private Origin origin;
    private SayHello sayHello;

    @Before
    public void setUp() {
        sayHello = mock(SayHello.class);
        origin = new Atlas()
                .context(new DefaultRetryer(5000L, 1000L, Collections.singletonList(Throwable.class)))
                .create(sayHello, Origin.class);
    }

    @Test
    public void shouldExecuteTargetMethod() {
        origin.hello();
        verify(sayHello, times(1)).hello();
    }

    @Test(expected = CustomException.class)
    public void shouldPropagateExceptionInTargetMethod() {
        doThrow(CustomException.class).when(sayHello).hello();
        origin.hello();
    }

    interface Origin extends SayHello {

    }

    interface SayHello {
        void hello();
    }

}
