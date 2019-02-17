package io.qameta.atlas.core;

import io.qameta.atlas.core.internal.DefaultMethodExtension;
import io.qameta.atlas.core.testdata.CustomException;
import org.junit.Before;
import org.junit.Test;

public class RetryerTest {

    private Atlas atlas;

    @Before
    public void initAtlas() {
        atlas = new Atlas()
                .extension(new DefaultMethodExtension()).timeouts(10000L).polling(1000L);
    }

    @Test(expected = CustomException.class)
    public void shouldUseGlobalRetryConfiguration() {
        InterfaceWithDefaultMethodThrowable instance = atlas
                .create(new Object(), InterfaceWithDefaultMethodThrowable.class);
        instance.doSomething();
    }

    public interface InterfaceWithDefaultMethodThrowable {
        default void doSomething() {
            throw new CustomException();
        }
    }
}
