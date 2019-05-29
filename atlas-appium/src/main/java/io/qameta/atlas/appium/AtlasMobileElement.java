package io.qameta.atlas.appium;


import io.qameta.atlas.webdriver.AtlasWebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;


/**
 * @author Artem Sokovets.
 */
public interface AtlasMobileElement extends AtlasWebElement {

    void longPress();

    /**
     * Swipe (Scroll) down to element.
     * @return {@link AtlasMobileElement}
     */
    AtlasMobileElement swipeDownOn();

    /**
     * Swipe (Scroll) up to element.
     * @return {@link AtlasMobileElement}
     */
    AtlasMobileElement swipeUpOn();

    /**
     * The same as {@link WrapsElement#getWrappedElement()}.
     */
    @Override
    WebElement getWrappedElement();
}
