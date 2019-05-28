package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AtlasWebElementMethodsTest {

    private AtlasWebElement atlasWebElement;

    private WebElement originWebElement;

    @Before
    public void initElements() {
        originWebElement = mockWebElement();
        atlasWebElement = new Atlas()
                .create(originWebElement, AtlasWebElement.class);
    }

    @Test
    public void clickMethodTest() {
        atlasWebElement.click();
        verify(originWebElement, times(1)).click();
    }

    @Test
    public void submitMethodTest() {
        atlasWebElement.submit();
        verify(originWebElement, times(1)).submit();
    }

    @Test
    public void sendKeysMethodTest() {
        atlasWebElement.sendKeys();
        verify(originWebElement, times(1)).sendKeys();
    }

    @Test
    public void clearMethodTest() {
        atlasWebElement.clear();
        verify(originWebElement, times(1)).clear();
    }

    @Test
    public void getTagNameMethodTest() {
        atlasWebElement.getTagName();
        verify(originWebElement, times(1)).getTagName();
    }

    @Test
    public void getAttributeMethodTest() {
        atlasWebElement.getAttribute("");
        verify(originWebElement, times(1)).getAttribute("");
    }

    @Test
    public void isSelectedMethodTest() {
        atlasWebElement.isSelected();
        verify(originWebElement, times(1)).isSelected();
    }

    @Test
    public void isEnabledMethodTest() {
        atlasWebElement.isEnabled();
        verify(originWebElement, times(1)).isEnabled();
    }

    @Test
    public void getTextMethodTest() {
        atlasWebElement.getText();
        verify(originWebElement, times(1)).getText();
    }

    @Test
    public void findElementsMethodTest() {
        atlasWebElement.findElements(By.xpath(""));
        verify(originWebElement, times(1)).findElements(By.xpath(""));
    }

    @Test
    public void findElementMethodTest() {
        atlasWebElement.findElement(By.xpath(""));
        verify(originWebElement, times(1)).findElement(By.xpath(""));
    }

    @Test
    public void isDisplayedMethodTest() {
        atlasWebElement.isDisplayed();
        verify(originWebElement, times(1)).isDisplayed();
    }

    @Test
    public void getLocationMethodTest() {
        atlasWebElement.getLocation();
        verify(originWebElement, times(1)).getLocation();
    }

    @Test
    public void getSizeMethodTest() {
        atlasWebElement.getSize();
        verify(originWebElement, times(1)).getSize();
    }

    @Test
    public void getRectMethodTest() {
        atlasWebElement.getRect();
        verify(originWebElement, times(1)).getRect();
    }

    @Test
    public void getCssValueMethodTest() {
        atlasWebElement.getCssValue("");
        verify(originWebElement, times(1)).getCssValue("");
    }

    @Test
    public void getCoordinatesMethodTest() {
        atlasWebElement.getCoordinates();
        verify((Locatable) originWebElement, times(1)).getCoordinates();
    }


    @Test
    public void toStringMethodTest() {
        assertThat(atlasWebElement.toString()).isEqualTo(originWebElement.toString());
    }

    @Test
    public void hashCodeMethodTest() {
        assertThat(atlasWebElement.hashCode()).isEqualTo(originWebElement.hashCode());
    }
}
