package io.qameta.atlas;

import io.qameta.atlas.extensions.FindBy;
import io.qameta.atlas.extensions.FindByExtension;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class NestedElementMethodTest {

    private static final String SELECTOR = "//div";

    @Test
    @Ignore("not implemented")
    public void nestedElementTest() {
        WebElement parent = mockWebElement();

        WebElement child = mockWebElement();
        when(parent.findElement(By.xpath(SELECTOR))).thenReturn(child);

        ParentElement parentElement = new Atlas()
                .extension(method -> method.isAnnotationPresent(FindBy.class), new FindByExtension())
                .create(parent, ParentElement.class);

        assertThat(parentElement.child()).isNotNull();
    }

    interface ParentElement extends AtlasWebElement {

        @FindBy(SELECTOR)
        ChildElement child();

    }

    interface ChildElement extends AtlasWebElement {

    }
}
