package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.FindByCollectionExtension;
import io.qameta.atlas.webdriver.extension.FindByExtension;
import io.qameta.atlas.webdriver.extension.Name;
import io.qameta.atlas.webdriver.extension.Param;
import io.qameta.atlas.webdriver.extension.ToStringMethodExtension;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class FindByWithNameTest {

    private static final String PARAM = "id";
    private static final String PARAM_VALUE = "val";
    private static final String SELECTOR = "//*[@id=\"{{ " + PARAM + " }}\"]";
    private static final String FINAL_SELECTOR = "//*[@id=\"" + PARAM_VALUE + "\"]";
    private static final String ELEMENT_NAME = "element {{ " + PARAM + " }}";
    private static final String FINAL_ELEMENT_NAME = "element " + PARAM_VALUE;

    private WebElement parent;

    private Atlas atlas;

    @Before
    public void createParent() {
        parent = mockWebElement();
        atlas = new Atlas()
                .extension(new ToStringMethodExtension())
                .extension(new FindByExtension())
                .extension(new FindByCollectionExtension());
    }

    @Test
    public void shouldUseTemplateInNameAnnotationForElement() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        assertThat(atlasWebElement.childWithName(PARAM_VALUE).toString()).isEqualTo(FINAL_ELEMENT_NAME);
    }

    @Test
    public void shouldUseTemplateInNameAnnotationForElementsCollection() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        assertThat(atlasWebElement.childListWithName(PARAM_VALUE).toString()).isEqualTo(FINAL_ELEMENT_NAME);
    }

    @Test
    public void shouldUseTemplateInNameAnnotationForCollectionElement() {
        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        when(parent.findElements(By.xpath(FINAL_SELECTOR))).thenReturn(singletonList(mockAtlasWebElement()));

        int first = 0;
        assertThat(atlasWebElement.childListWithName(PARAM_VALUE).get(first).toString())
                .isEqualTo(listElementName(FINAL_ELEMENT_NAME, first));
    }

    interface ParentElement extends AtlasWebElement {

        @Name(ELEMENT_NAME)
        @FindBy(SELECTOR)
        AtlasWebElement childWithName(@Param(PARAM) String id);

        @Name(ELEMENT_NAME)
        @FindBy(SELECTOR)
        ElementsCollection<AtlasWebElement> childListWithName(@Param(PARAM) String id);

    }

    private String listElementName(final String name, final int position) {
        return String.format("%s [%s]", name, position);
    }

}
