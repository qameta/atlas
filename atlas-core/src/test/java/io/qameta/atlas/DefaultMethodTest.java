package io.qameta.atlas;

import io.qameta.atlas.testdata.CustomException;
import org.junit.Test;

public class DefaultMethodTest {

    @Test
    public void shouldExecuteDefaultMethod() {
        InterfaceWithDefaultMethod instance = new Atlas()
                .create(new Object(), InterfaceWithDefaultMethod.class);
        instance.doSomething();
    }

    @Test(expected = CustomException.class)
    public void shouldPropagateExceptionInDefaultMethod() {
        InterfaceWithDefaultMethodThrowable instance = new Atlas()
                .create(new Object(), InterfaceWithDefaultMethodThrowable.class);
        instance.doSomething();
    }

    public interface InterfaceWithDefaultMethod {

        default void doSomething() {
        }

    }

    public interface InterfaceWithDefaultMethodThrowable {

        default void doSomething() {
            throw new CustomException();
        }

    }
}
