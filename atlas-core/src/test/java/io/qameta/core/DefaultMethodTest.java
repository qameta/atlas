package io.qameta.core;

import io.qameta.core.internal.DefaultMethodExtension;
import io.qameta.core.testdata.CustomException;
import org.junit.Before;
import org.junit.Test;

public class DefaultMethodTest {

    private Atlas atlas;

    @Before
    public void initAtlas() {
        atlas = new Atlas()
                .extension(new DefaultMethodExtension());
    }

    @Test
    public void shouldExecuteDefaultMethod() {
        InterfaceWithDefaultMethod instance = atlas
                .create(new Object(), InterfaceWithDefaultMethod.class);
        instance.doSomething();
    }

    @Test(expected = CustomException.class)
    public void shouldPropagateExceptionInDefaultMethod() {
        InterfaceWithDefaultMethodThrowable instance = atlas
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
