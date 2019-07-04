package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.extension.IsExtension;
import io.qameta.atlas.webdriver.extension.RetryAnnotationExtension;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockAtlasWebElement;
import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class IsExtensionTest {

    @Test
    public void noWaitForNestedSearch() {
        WebElement webElement = mockAtlasWebElement();
        when(webElement.findElement(By.xpath("//div"))).thenThrow(new NotFoundException());

        Atlas atlas = new Atlas(new WebDriverConfiguration(mockWebDriver()));
        atlas.extension(new RetryAnnotationExtension());
        atlas.extension(new IsExtension());
        final WebElement element = atlas.create(webElement, WebElement.class);

        assertThat(element.isDisplayed()).describedAs("Element should not visible").isFalse();
        verify(webElement, times(1)).isDisplayed();
        verify(webElement, timeout(0)).isDisplayed();
    }

}
