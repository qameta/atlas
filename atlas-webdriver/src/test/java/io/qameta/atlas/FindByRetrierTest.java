package io.qameta.atlas;

import io.qameta.atlas.api.Retry;
import io.qameta.atlas.extension.FindBy;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockWebDriver;
import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static org.mockito.Mockito.when;

public class FindByRetrierTest {

    @Test
    public void testOutput() {
        WebElement parentOrigin = mockWebElement();
        when(parentOrigin.findElement(By.xpath("//div"))).thenThrow(new NotFoundException());

        Atlas atlas = new Atlas(new WebDriverConfiguration(mockWebDriver()));
        ParentElement parent = atlas.create(parentOrigin, ParentElement.class);

        parent.child().isDisplayed();

    }

    interface ParentElement extends AtlasWebElement {

        @Retry(timeout = 50000L)
        @FindBy("//div")
        AtlasWebElement child();

    }

}
