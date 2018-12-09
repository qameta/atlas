package io.qameta.atlas.appium.extension;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.appium.context.AppiumDriverContext;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;

/**
 *  LongPress.
 */
public class LongPressExtension implements MethodExtension {

    private static final Duration LONG_TAP_DURATION = Duration.of(1000, ChronoUnit.MILLIS);

    @Override
    public boolean test(final Method method) {
        return method.getName().equals("longPress");
    }

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration configuration) {
        final AppiumDriver driver = configuration.getContext(AppiumDriverContext.class)
                .orElseThrow(() -> new AtlasException("WebDriver is missing")).getValue();

        final TouchAction action = new TouchAction(driver);
        final Point location = ((WebElement) proxy).getLocation();
        final Dimension size = ((WebElement) proxy).getSize();
        final int x = location.getX() + size.width / 2;
        final int y = location.getY() + size.height / 2;
        action.longPress(longPressOptions().withDuration(LONG_TAP_DURATION)
                .withPosition(new PointOption().withCoordinates(x, y))).perform();
        return proxy;
    }
}
