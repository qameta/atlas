package io.qameta.atlas;

import io.appium.java_client.MobileElement;
import io.appium.java_client.touch.LongPressOptions;
import org.openqa.selenium.internal.WrapsElement;


public interface AtlasMobileElement<T extends MobileElement> extends AtlasWebElement {

    void longPress();

    void swipeLeftOn();

    void swipeRightOn();

    AtlasMobileElement swipeDownOn();

    AtlasMobileElement swipeUpOn();

    boolean isDisplayed();

    /**
     * The same as {@link WrapsElement#getWrappedElement()}.
     */
    MobileElement getWrappedElement();
}