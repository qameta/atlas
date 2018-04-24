package io.qameta.atlas;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;

import java.util.List;

/**
 * Atlas Web Element.
 */
public interface AtlasWebElement extends WebElement, Locatable {

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

}
