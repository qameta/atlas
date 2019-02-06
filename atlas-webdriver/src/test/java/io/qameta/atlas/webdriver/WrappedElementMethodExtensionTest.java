package io.qameta.atlas.webdriver;


import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.extension.WrappedElementMethodExtension;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WrappedElementMethodExtensionTest {

    private AtlasWebElement atlasWebElement;
    private WebElement originWebElement;

    @Before
    public void initElements() {
        originWebElement = mockWebElement();
        atlasWebElement = new Atlas()
                .extension(new WrappedElementMethodExtension())
                .create(originWebElement, AtlasWebElement.class);
    }

    @Test
    public void getWrappedElementMethodTest() {
        WebElement element = atlasWebElement.getWrappedElement();
        element.click();
        verify(originWebElement, times(1)).click();
    }
}
