package io.qameta.atlas.extension;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.atlas.AtlasException;
import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.context.AppiumDriverContext;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Swipe (Scroll) up to element.
 */
public class SwipeUpOnExtension implements MethodExtension {

    private static final Duration SWIPE_DURATION = Duration.of(1000, ChronoUnit.MILLIS);

    @Override
    public boolean test(final Method method) {
        return method.getName().equals("swipeUpOn");
    }

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration configuration) throws Throwable {
        final AppiumDriver driver = configuration.getContext(AppiumDriverContext.class)
                .orElseThrow(() -> new AtlasException("WebDriver is missing")).getValue();

        final int xStart = driver.manage().window().getSize().width / 2;
        final int yStart = driver.manage().window().getSize().height / 8;
        final int xEnd = xStart;
        final int yEnd = driver.manage().window().getSize().height * 4 / 5;

        final TouchAction action = new TouchAction(driver);
        action.press(new PointOption().withCoordinates(xStart, yStart))
                .waitAction(new WaitOptions().withDuration(SWIPE_DURATION))
                .moveTo(new PointOption().withCoordinates(xEnd, yEnd))
                .release()
                .perform();
        return proxy;
    }
}
