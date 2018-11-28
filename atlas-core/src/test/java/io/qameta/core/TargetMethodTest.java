package io.qameta.core;

import io.qameta.core.testdata.CustomException;
import org.junit.Test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TargetMethodTest {

    @Test
    public void shouldExecuteTargetMethod() {
        SayHello sayHello = mock(SayHello.class);

        Origin origin = new Atlas()
                .create(sayHello, Origin.class);
        origin.hello();

        verify(sayHello, times(1)).hello();
    }

    @Test(expected = CustomException.class)
    public void shouldPropagateExceptionInTargetMethod() {
        SayHello sayHello = mock(SayHello.class);
        doThrow(CustomException.class).when(sayHello).hello();

        Origin origin = new Atlas()
                .create(sayHello, Origin.class);
        origin.hello();
    }


    interface Origin extends SayHello {

    }

    interface SayHello {

        void hello();

    }

}
