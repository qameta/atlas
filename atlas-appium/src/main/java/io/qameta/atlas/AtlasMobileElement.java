package io.qameta.atlas;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.internal.WrapsElement;


/**
 * @author Artem Sokoevts.
 */
public interface AtlasMobileElement extends AtlasWebElement {

    void longPress();

    AtlasMobileElement swipeUpOn();

    /**
     * The same as {@link WrapsElement#getWrappedElement()}.
     */
    MobileElement getWrappedElement();
}
