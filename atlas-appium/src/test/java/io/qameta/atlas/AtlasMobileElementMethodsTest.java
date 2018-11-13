package io.qameta.atlas;

import io.appium.java_client.MobileElement;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static io.qameta.atlas.testdata.ObjectFactory.mockAppiumElement;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Check methods of AtlasMobileElement, that it are invoked.
 */
public class AtlasMobileElementMethodsTest {

    private AtlasMobileElement atlasMobileElement;
    private MobileElement originMobileElement;

    @Before
    public void initElements() {
        originMobileElement = mockAppiumElement();
        atlasMobileElement = new Atlas()
                .create(originMobileElement, AtlasMobileElement.class);
    }

    @Test
    public void clickMethodTest() {
        atlasMobileElement.click();
        verify(originMobileElement, times(1)).click();
    }

    @Test
    public void sendKeysMethodTest() {
        atlasMobileElement.sendKeys();
        verify(originMobileElement, times(1)).sendKeys();
    }

    @Test
    public void isEnabledMethodTest() {
        atlasMobileElement.isEnabled();
        verify(originMobileElement, times(1)).isEnabled();
    }

    @Test
    public void getTextMethodTest() {
        atlasMobileElement.getText();
        verify(originMobileElement, times(1)).getText();
    }

    @Test
    public void findElementsMethodTest() {
        atlasMobileElement.findElements(By.xpath(""));
        verify(originMobileElement, times(1)).findElements(By.xpath(""));
    }

    @Test
    public void isDisplayedMethodTest() {
        atlasMobileElement.isDisplayed();
        verify(originMobileElement, times(1)).isDisplayed();
    }
}
