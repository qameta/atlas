package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.api.Timeout;
import io.qameta.atlas.webdriver.extension.ShouldMethodExtension;
import io.qameta.atlas.webdriver.extension.WaitUntilMethodExtension;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;

import java.util.List;

/**
 * Atlas Web Element.
 * @param <T> the type of the value being boxed
 */
public interface AtlasWebElement<T extends WebElement> extends WrapsElement, WebElement, Locatable {

    /**
     * The same as {@link WebElement#click()}.
     */
    @Override
    void click();

    /**
     * The same as {@link WebElement#submit()}.
     */
    @Override
    void submit();

    /**
     * The same as {@link WebElement#sendKeys(CharSequence...)}.
     */
    @Override
    void sendKeys(CharSequence... keysToSend);

    /**
     * The same as {@link WebElement#clear()}.
     */
    @Override
    void clear();

    /**
     * The same as {@link WebElement#getTagName()}.
     */
    @Override
    String getTagName();

    /**
     * The same as {@link WebElement#getAttribute(String)}.
     */
    @Override
    String getAttribute(String name);

    /**
     * The same as {@link WebElement#isSelected()}.
     */
    @Override
    boolean isSelected();

    /**
     * The same as {@link WebElement#isEnabled()}.
     */
    @Override
    boolean isEnabled();

    /**
     * The same as {@link WebElement#getText()}.
     */
    @Override
    String getText();

    /**
     * The same as {@link WebElement#findElements(By)}.
     */
    @Override
    List<WebElement> findElements(By by);

    /**
     * The same as {@link WebElement#findElement(By)}.
     */
    @Override
    WebElement findElement(By by);

    /**
     * The same as {@link WebElement#isDisplayed()}.
     */
    @Override
    boolean isDisplayed();

    /**
     * The same as {@link WebElement#getLocation()}.
     */
    @Override
    Point getLocation();

    /**
     * The same as {@link WebElement#getSize()}.
     */
    @Override
    Dimension getSize();

    /**
     * The same as {@link WebElement#getRect()}.
     */
    @Override
    Rectangle getRect();

    /**
     * The same as {@link WebElement#getCssValue(String)}.
     */
    @Override
    String getCssValue(String propertyName);

    /**
     * The same as {@link Locatable#getCoordinates()}.
     */
    @Override
    Coordinates getCoordinates();

    /**
     * This method handled by the {@link ShouldMethodExtension}.
     */
    T should(Matcher matcher);

    /**
     * This method handled by the {@link ShouldMethodExtension}.
     */
    T should(Matcher matcher, @Timeout Integer timeoutInSeconds);

    /**
     * This method handled by the {@link ShouldMethodExtension}.
     */
    T should(String message, Matcher matcher);

    /**
     * This method handled by the {@link ShouldMethodExtension}.
     */
    T should(String message, Matcher matcher, @Timeout Integer timeoutInSeconds);

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
    @Override
    WebElement getWrappedElement();

    /**
     * Executes JavaScript in the context of the currently AtlasWebElement.
     */
    Object executeScript(String script);

}
