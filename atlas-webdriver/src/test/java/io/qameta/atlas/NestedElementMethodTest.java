package io.qameta.atlas;

import io.qameta.atlas.extension.FindBy;
import io.qameta.atlas.extension.FindByExtension;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class NestedElementMethodTest {

    private static final String SELECTOR = "//div";
    private static final boolean IS_DISPLAYED = true;

    @Test
    public void shouldFindNestedElement() {
        WebElement parent = mockWebElement();

        WebElement child = mockWebElement();
        when(parent.findElement(By.xpath(SELECTOR))).thenReturn(child);
        when(child.isDisplayed()).thenReturn(IS_DISPLAYED);

        ParentElement parentElement = new Atlas()
                .extension(new FindByExtension())
                .create(parent, ParentElement.class);

        assertThat(parentElement.child().isDisplayed()).isEqualTo(IS_DISPLAYED);
    }

    interface ParentElement extends AtlasWebElement {

        @FindBy(SELECTOR)
        ChildElement child();

    }

    interface ChildElement extends AtlasWebElement {

    }
}
