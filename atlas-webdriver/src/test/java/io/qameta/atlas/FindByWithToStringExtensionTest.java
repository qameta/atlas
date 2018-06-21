package io.qameta.atlas;

import io.qameta.atlas.extension.FindBy;
import io.qameta.atlas.extension.FindByCollectionExtension;
import io.qameta.atlas.extension.FindByExtension;
import io.qameta.atlas.extension.Name;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockAtlasWebElement;
import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kurau (Yuri Kalinin)
 */
@Ignore("Name is not implemented yet")
public class FindByWithToStringExtensionTest {

    private static final String SELECTOR = "//div";
    private static final String ELEMENT_NAME = "my name";
    private static final String METHOD_NAME = "childWithoutName";
    private static final String METHOD_LIST_NAME = "childList";

    private WebElement parent;

    @Before
    public void createParent() {
        parent = mockWebElement();
    }

    @Test
    public void shouldUseNameAnnotationInFindByExtension() {
        ParentElement atlasWebElement = new Atlas()
                .extension(new FindByExtension())
                .create(parent, ParentElement.class);

        assertThat(atlasWebElement.childWithName().toString()).isEqualTo(ELEMENT_NAME);
    }

    @Test
    public void shouldUseMethodNameInFindByExtension() {
        ParentElement atlasWebElement = new Atlas()
                .extension(new FindByExtension())
                .create(parent, ParentElement.class);

        assertThat(atlasWebElement.childWithoutName().toString()).isEqualTo(METHOD_NAME);
    }

    @Test
    public void shouldUseMethodNameInFindByCollectionExtension() {
        ParentElement atlasWebElement = new Atlas()
                .extension(new FindByCollectionExtension())
                .create(parent, ParentElement.class);

        assertThat(atlasWebElement.childList().toString()).isEqualTo(METHOD_LIST_NAME);
    }

    @Test
    public void shouldUseNameAnnotationInFindByCollectionExtension() {
        ParentElement atlasWebElement = new Atlas()
                .extension(new FindByCollectionExtension())
                .create(parent, ParentElement.class);

        assertThat(atlasWebElement.childListWithName().toString()).isEqualTo(ELEMENT_NAME);
    }

    @Test
    public void shouldAppendIndexToElementNameFromCollection() {
        ParentElement atlasWebElement = new Atlas()
                .extension(new FindByCollectionExtension())
                .create(parent, ParentElement.class);
        when(parent.findElements(By.xpath(SELECTOR))).thenReturn(asList(mockAtlasWebElement()));

        int first = 0;
        assertThat(atlasWebElement.childListWithName().get(first).toString())
                .isEqualTo(listElementName(ELEMENT_NAME, first));
    }

    @Test
    public void shouldAppendIndexToMethodNameFromCollection() {
        ParentElement atlasWebElement = new Atlas()
                .extension(new FindByCollectionExtension())
                .create(parent, ParentElement.class);
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
