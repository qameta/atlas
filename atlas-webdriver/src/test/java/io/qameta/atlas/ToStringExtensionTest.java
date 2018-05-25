package io.qameta.atlas;

import io.qameta.atlas.extensions.FindBy;
import io.qameta.atlas.extensions.FindByExtension;
import io.qameta.atlas.extensions.Name;
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

    private static final String SELECTOR = "//div";
    private static final String ELEMENT_NAME = "my name";
    private static final String METHOD_NAME = "childWithoutName";

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

    @Test
    public void shouldUseNameAnnotationInFindByExtension() {
        ParentElement atlasWebElement = new Atlas()
                .extension(new FindByExtension())
                .extension(new ToStringExtension(message))
                .create(parent, ParentElement.class);

        assertThat(atlasWebElement.childWithName().toString()).isEqualTo(ELEMENT_NAME);
    }

    @Test
    public void shouldUseMethodNameInFindByExtension() {
        ParentElement atlasWebElement = new Atlas()
                .extension(new FindByExtension())
                .extension(new ToStringExtension(message))
                .create(parent, ParentElement.class);

        assertThat(atlasWebElement.childWithoutName().toString()).isEqualTo(METHOD_NAME);
    }

    interface ParentElement extends AtlasWebElement {

        @Name(ELEMENT_NAME)
        @FindBy(SELECTOR)
        ChildElement childWithName();

        @FindBy(SELECTOR)
        ChildElement childWithoutName();

    }

    interface ChildElement extends AtlasWebElement {

    }
}
