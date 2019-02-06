package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.api.Timeout;
import io.qameta.atlas.webdriver.extension.ShouldMethodExtension;
import io.qameta.atlas.webdriver.extension.WaitUntilMethodExtension;
import org.hamcrest.Matcher;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;

/**
 * Atlas Web Element.
 * @param <T> the type of the value being boxed
 */
public interface AtlasWebElement<T extends WebElement> extends WrapsElement, WebElement, Locatable {

    /**
     * The same as {@link WebElement#click()}.
     */
    void click();

    /**
     * The same as {@link WebElement#submit()}.
     */
    void submit();

    /**
     * The same as {@link WebElement#sendKeys(CharSequence...)}.
     */
    void sendKeys(CharSequence... keysToSend);

    /**
     * The same as {@link WebElement#clear()}.
     */
    void clear();

    /**
     * The same as {@link WebElement#getTagName()}.
     */
    String getTagName();

    /**
     * The same as {@link WebElement#getAttribute(String)}.
     */
    String getAttribute(String name);

    /**
     * The same as {@link WebElement#isSelected()}.
     */
    boolean isSelected();

    /**
     * The same as {@link WebElement#isEnabled()}.
     */
    boolean isEnabled();

    /**
     * The same as {@link WebElement#getText()}.
     */
    String getText();

    /**
     * The same as {@link WebElement#findElements(By)}.
     */
    List<WebElement> findElements(By by);

    /**
     * The same as {@link WebElement#findElement(By)}.
     */
    WebElement findElement(By by);

    /**
     * The same as {@link WebElement#isDisplayed()}.
     */
    boolean isDisplayed();

    /**
     * The same as {@link WebElement#getLocation()}.
     */
    Point getLocation();

    /**
     * The same as {@link WebElement#getSize()}.
     */
    Dimension getSize();

    /**
     * The same as {@link WebElement#getRect()}.
     */
    Rectangle getRect();

    /**
     * The same as {@link WebElement#getCssValue(String)}.
     */
    String getCssValue(String propertyName);

    /**
     * The same as {@link Locatable#getCoordinates()}.
     */
    Coordinates getCoordinates();

    /**
     * This method handled by the {@link ShouldMethodExtension}.
     */
    T should(Matcher matcher);

    /**
     * This method handled by the {@link ShouldMethodExtension}.
     */
    T should(String message, Matcher matcher);

    /**
     * This method handled by the {@link WaitUntilMethodExtension}.
     */
    T waitUntil(Matcher matcher);

    /**
     * This method handled by the {@link WaitUntilMethodExtension}.
     */
    T waitUntil(Matcher matcher, @Timeout Integer timeoutInSeconds);

    /**
     * This method handled by the {@link WaitUntilMethodExtension}.
     */
    T waitUntil(String message, Matcher matcher);

    /**
     * This method handled by the {@link WaitUntilMethodExtension}.
     */
    T waitUntil(String message, Matcher matcher, @Timeout Integer timeoutInSeconds);

    /**
     * The same as {@link WrapsElement#getWrappedElement()}.
     */
    WebElement getWrappedElement();

    /**
     * Executes JavaScript in the context of the currently AtlasWebElement.
     */
    Object executeScript(String script);

}
