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

public class SwipeUpOnExtension implements MethodExtension {

    private static final Duration SWIPE_DURATION = Duration.of(1000, ChronoUnit.MILLIS);

    @Override
    public boolean test(Method method) {
        return method.getName().equals("swipeUpOn");
    }

    @Override
    public Object invoke(Object proxy, MethodInfo methodInfo, Configuration configuration) throws Throwable {
        final AppiumDriver driver = configuration.getContext(AppiumDriverContext.class).
                orElseThrow(() -> new AtlasException("WebDriver is missing")).getValue();

        int xStart = driver.manage().window().getSize().width / 2;
        int yStart = driver.manage().window().getSize().height / 8;
        int xEnd = xStart;
        int yEnd = driver.manage().window().getSize().height * 4 / 5;

        TouchAction action = new TouchAction(driver);
        action.press(new PointOption().withCoordinates(xStart, yStart))
                .waitAction(new WaitOptions().withDuration(SWIPE_DURATION))
                .moveTo(new PointOption().withCoordinates(xEnd, yEnd))
                .release()
                .perform();
        return proxy;
    }
}
