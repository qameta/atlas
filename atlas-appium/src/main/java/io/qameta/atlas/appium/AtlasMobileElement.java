package io.qameta.atlas.appium;


import io.qameta.atlas.webdriver.AtlasWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;


/**
 * @author Artem Sokoevts.
 */
public interface AtlasMobileElement extends AtlasWebElement {

    void longPress();

    AtlasMobileElement swipeDownOn();

    /**
     * The same as {@link WrapsElement#getWrappedElement()}.
     */
    WebElement getWrappedElement();
}
