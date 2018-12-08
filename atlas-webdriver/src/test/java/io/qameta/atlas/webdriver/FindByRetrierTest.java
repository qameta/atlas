package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.api.Retry;
import io.qameta.atlas.webdriver.extension.FindBy;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebDriver;
import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
import static org.mockito.Mockito.when;

public class FindByRetrierTest {

    @Test(expected = NotFoundException.class)
    public void retryChildFind() {
        WebElement parentOrigin = mockWebElement();
        WebElement childOrigin = mockWebElement();

        when(parentOrigin.findElement(By.xpath("//div"))).thenThrow(new NotFoundException());
        when(childOrigin.isDisplayed()).thenThrow(new NotFoundException());

        Atlas atlas = new Atlas(new WebDriverConfiguration(mockWebDriver()));
        ParentElement parent = atlas.create(parentOrigin, ParentElement.class);

        parent.child().isDisplayed();
    }

    interface ParentElement extends AtlasWebElement {
        @Retry(timeout = 8000)
        @FindBy("//div")
        NestedElement child();
    }

    interface NestedElement extends AtlasWebElement {
        boolean isDisplayed();
    }
}
