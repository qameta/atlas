package io.qameta.appium;

import io.qameta.appium.annotations.AndroidFindBy;
import io.qameta.core.Atlas;
import io.qameta.core.api.Retry;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import static io.qameta.appium.testdata.ObjectFactory.mockAndroidDriver;
import static io.qameta.appium.testdata.ObjectFactory.mockWebElement;
import static org.mockito.Mockito.when;

/**
 * Check retries functional for AtlasMobileElement.
 */
public class AppiumFindByRetrierTest {

    @Test(expected = NotFoundException.class)
    public void retryChildFind() {
        final WebElement parentOrigin = mockWebElement();
        final WebElement childOrigin = mockWebElement();

        when(parentOrigin.findElement(By.xpath("//div"))).thenThrow(new NotFoundException());
        when(childOrigin.findElement(By.xpath("//"))).thenThrow(new NotFoundException());

        final Atlas atlas = new Atlas(new AppiumDriverConfiguration(mockAndroidDriver()));
        final ParentElement parent = atlas.create(parentOrigin, ParentElement.class);

        parent.child().isDisplayed();
    }

    /**
     * Parent mobile element.
     */
    private interface ParentElement extends AtlasMobileElement {
        @Retry(timeout = 1000L)
        @AndroidFindBy(xpath = "//div")
        NestedElement child();
    }

    /**
     * Child mobile element.
     */
    private interface NestedElement extends AtlasMobileElement {

    }

}
