package io.qameta.atlas.appium;

import io.qameta.atlas.appium.extension.LongPressExtension;
import io.qameta.atlas.core.Atlas;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.appium.testdata.ObjectFactory.mockAndroidDriver;
import static io.qameta.atlas.appium.testdata.ObjectFactory.mockWebElement;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Check long press method of AtlasMobileElement, that it are invoked.
 */
public class LongPressExtensionTest {

    private AtlasMobileElement mobileElement;
    private WebElement parentElement = mockWebElement();

    @Before
    public void createAtlasElementWithExtension() {
        mobileElement = new Atlas(new AppiumDriverConfiguration(mockAndroidDriver()))
                .extension(new LongPressExtension())
                .create(parentElement, AtlasMobileElement.class);
    }


    @Test
    public void shouldInvokeLongPressExtensionMethodName() {
        when(parentElement.getLocation()).thenReturn(mock(Point.class));
        when(parentElement.getSize()).thenReturn(mock(Dimension.class));
        mobileElement.longPress();
    }
}
