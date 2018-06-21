package io.qameta.atlas;

import io.qameta.atlas.extension.FindBy;
import io.qameta.atlas.extension.FindByExtension;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NestedElementMethodTest {

    private static final String CHILD_SELECTOR = "//div";
    private static final String LEAF_SELECTOR = "//div";

    @Test
    @Ignore("Await implementing lazy search")
    public void shouldFindNestedElement() {
        WebElement parent = mockWebElement();

        WebElement child = mockWebElement();
        when(parent.findElement(By.xpath(CHILD_SELECTOR))).thenReturn(child);

        WebElement leaf = mockWebElement();
        when(child.findElement(By.xpath(LEAF_SELECTOR))).thenReturn(leaf);

        ParentElement parentElement = new Atlas()
                .extension(new FindByExtension())
                .create(parent, ParentElement.class);

        assertThat(parentElement.child()).isNotNull();

        parentElement.child().isDisplayed();
        verify(child).isDisplayed();

        assertThat(parentElement.child().leaf()).isNotNull();

        parentElement.child().leaf().isDisplayed();
        verify(leaf).isDisplayed();
    }

    interface ParentElement extends AtlasWebElement {

        @FindBy(CHILD_SELECTOR)
        ChildElement child();

    }

    interface ChildElement extends AtlasWebElement {

        @FindBy(LEAF_SELECTOR)
        LeafElement leaf();

    }

    interface LeafElement extends AtlasWebElement {

    }
}
