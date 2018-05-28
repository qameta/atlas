package io.qameta.atlas;

import io.qameta.atlas.extensions.ToStringExtension;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kurau (Yuri Kalinin)
 */
public class ToStringExtensionTest {

    private String message;
    private WebElement parent;

    @Before
    public void createParent() {
        message = RandomStringUtils.randomAlphanumeric(10);
        parent = mockWebElement();
    }

    @Test
    public void shouldUseDefaultToStringMethodWithoutExtension() {
        AtlasWebElement atlasWebElement = new Atlas().create(parent, AtlasWebElement.class);
        when(parent.toString()).thenReturn(message);

        assertThat(atlasWebElement.toString()).isEqualTo(message);
    }

    @Test
    public void shouldUseToStringExtensionMethodName() {
        AtlasWebElement atlasWebElement = new Atlas()
                .extension(new ToStringExtension(message))
                .create(parent, AtlasWebElement.class);

        assertThat(atlasWebElement.toString()).isEqualTo(message);
    }
}
