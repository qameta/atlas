package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.FindByExtension;
import io.qameta.atlas.webdriver.extension.Name;
import io.qameta.atlas.webdriver.extension.ToStringMethodExtension;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kurau (Yuri Kalinin)
 */
public class FindByWithToStringExtensionTest {

    private static final String SELECTOR = "//div";
    private static final String ELEMENT_NAME = "my name";
    private static final String METHOD_NAME = "childWithoutName";
    private static final String METHOD_LIST_NAME = "childList";

    private WebElement parent;

    private Atlas atlas;

    @Before
    public void createParent() {
        parent = mockWebElement();
        atlas = new Atlas()
                .extension(new ToStringMethodExtension())
                .extension(new FindByExtension())
                .context(mockDefaultRetryer());
    }

    @Test
    public void shouldUseNameAnnotationInFindByExtension() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        assertThat(atlasWebElement.childWithName().toString()).isEqualTo(ELEMENT_NAME);
    }

    @Test
    public void shouldUseMethodNameInFindByExtension() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        assertThat(atlasWebElement.childWithoutName().toString()).isEqualTo(METHOD_NAME);
    }

    @Test
    @Ignore("Fix element name in collection")
    public void shouldUseMethodNameInFindByCollectionExtension() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        assertThat(atlasWebElement.childList().toString()).isEqualTo(METHOD_LIST_NAME);
    }

    @Test
    @Ignore("Fix element name in collection")
    public void shouldUseNameAnnotationInFindByCollectionExtension() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        assertThat(atlasWebElement.childListWithName().toString()).isEqualTo(ELEMENT_NAME);
    }

    @Test
    @Ignore("Fix element name in collection")
    public void shouldAppendIndexToElementNameFromCollection() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        when(parent.findElements(By.xpath(SELECTOR))).thenReturn(asList(mockAtlasWebElement()));

        int first = 0;
        assertThat(atlasWebElement.childListWithName().get(first).toString())
                .isEqualTo(listElementName(ELEMENT_NAME, first));
    }

    @Test
    @Ignore("Fix element name in collection")
    public void shouldAppendIndexToMethodNameFromCollection() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        when(parent.findElements(By.xpath(SELECTOR))).thenReturn(asList(mockAtlasWebElement()));

        int first = 0;
        assertThat(atlasWebElement.childList().get(first).toString())
                .isEqualTo(listElementName(METHOD_LIST_NAME, first));
    }

    interface ParentElement extends AtlasWebElement {

        @Name(ELEMENT_NAME)
        @FindBy(SELECTOR)
        ChildElement childWithName();

        @FindBy(SELECTOR)
        ChildElement childWithoutName();

        @Name(ELEMENT_NAME)
        @FindBy(SELECTOR)
        ElementsCollection<ChildElement> childListWithName();

        @FindBy(SELECTOR)
        ElementsCollection<ChildElement> childList();

    }

    interface ChildElement extends AtlasWebElement {

    }

    private String listElementName(String name, int index) {
        return String.format("%s [%s]", name, index);
    }
}
