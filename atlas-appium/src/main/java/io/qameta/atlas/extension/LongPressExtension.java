package io.qameta.atlas.extension;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.atlas.AtlasException;
import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.context.AppiumDriverContext;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;

public class LongPressExtension implements MethodExtension {

    private static final Duration LONG_TAP_DURATION = Duration.of(1000, ChronoUnit.MILLIS);

    @Override
    public boolean test(Method method) {
        return method.getName().equals("longTap");
    }

    @Override
    public Object invoke(Object proxy, MethodInfo methodInfo, Configuration configuration) throws Throwable {
        final AppiumDriver driver = configuration.getContext(AppiumDriverContext.class).
                orElseThrow(() -> new AtlasException("WebDriver is missing")).getValue();

        TouchAction action = new TouchAction(driver);
        Point location = ((WebElement) proxy).getLocation();
        Dimension size = ((WebElement) proxy).getSize();
        int x = location.getX() + size.width / 2;
        int y = location.getY() + size.height / 2;
        action.longPress(longPressOptions().withDuration(LONG_TAP_DURATION)
                .withPosition(new PointOption().withCoordinates(x, y))).perform();
        return proxy;
    }
}
