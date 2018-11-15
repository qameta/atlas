package io.qameta.atlas;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockAppiumElement;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Check methods of AtlasMobileElement, that it are invoked.
 */
public class AtlasMobileElementMethodsTest {

    private AtlasMobileElement atlasMobileElement;
    private WebElement originElement;

    @Before
    public void initElements() {
        originElement = mockAppiumElement();
        atlasMobileElement = new Atlas()
                .create(originElement, AtlasMobileElement.class);
    }

    @Test
    public void clickMethodTest() {
        atlasMobileElement.click();
        verify(originElement, times(1)).click();
    }

    @Test
    public void sendKeysMethodTest() {
        atlasMobileElement.sendKeys();
        verify(originElement, times(1)).sendKeys();
    }

    @Test
    public void isEnabledMethodTest() {
        atlasMobileElement.isEnabled();
        verify(originElement, times(1)).isEnabled();
    }

    @Test
    public void getTextMethodTest() {
        atlasMobileElement.getText();
        verify(originElement, times(1)).getText();
    }

    @Test
    public void findElementsMethodTest() {
        atlasMobileElement.findElements(By.xpath(""));
        verify(originElement, times(1)).findElements(By.xpath(""));
    }

    @Test
    public void isDisplayedMethodTest() {
        atlasMobileElement.isDisplayed();
        verify(originElement, times(1)).isDisplayed();
    }
}
