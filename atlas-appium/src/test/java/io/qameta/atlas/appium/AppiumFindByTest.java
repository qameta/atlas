package io.qameta.atlas.appium;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.appium.annotations.AndroidFindBy;
import io.qameta.atlas.appium.extension.AppiumFindByExtension;
import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.extension.Name;
import io.qameta.atlas.webdriver.extension.Param;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.appium.testdata.ObjectFactory.mockAndroidDriver;
import static io.qameta.atlas.appium.testdata.ObjectFactory.mockWebElement;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AppiumFindByTest {

    private WebElement parent;
    private Atlas atlas;

    @Before
    public void setUp() {
        parent = mockWebElement();
        atlas = new Atlas(new AppiumDriverConfiguration(mockAndroidDriver()))
                .extension(new AppiumFindByExtension());
    }

    @Test
    public void shouldParameterizedFindBy() {
        when(parent.findElement(By.xpath(anyString()))).thenReturn(mockWebElement());

        String param = randomAlphanumeric(10);

        ParentElement atlasMobileElement = atlas.create(parent, ParentElement.class);
        atlasMobileElement.childElement(param).isDisplayed();

        verify(parent, times(1)).findElement(By.xpath(String.format("//div[%s]", param)));
    }

    @Test
    public void shouldParameterizedName() {
        when(parent.findElement(By.xpath(anyString()))).thenReturn(mockWebElement());

        String param = randomAlphanumeric(10);

        ParentElement atlasMobileElement = atlas.create(parent, ParentElement.class);

        assertEquals(String.format("element %s", param), atlasMobileElement.childElement(param).toString());
    }

    private interface ParentElement extends AtlasWebElement {
        @Name("element {{ value }}")
        @AndroidFindBy(xpath = "//div[{{ value }}]")
        AtlasWebElement childElement(@Param("value") String value);
    }



}
