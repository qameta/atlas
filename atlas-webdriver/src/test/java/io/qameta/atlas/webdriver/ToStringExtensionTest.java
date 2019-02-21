package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.extension.ToStringMethodExtension;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockDefaultRetryer;
import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
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
        AtlasWebElement atlasWebElement = new Atlas()
                .context(mockDefaultRetryer())
                .create(parent, AtlasWebElement.class);
        when(parent.toString()).thenReturn(message);

        assertThat(atlasWebElement.toString()).isEqualTo(message);
    }

    @Test
    public void shouldUseToStringExtensionMethodName() {
        AtlasWebElement atlasWebElement = new Atlas()
                .extension(new ToStringMethodExtension())
                .context(mockDefaultRetryer())
                .create(message, parent, AtlasWebElement.class);

        assertThat(atlasWebElement.toString()).isEqualTo(message);
    }
}
