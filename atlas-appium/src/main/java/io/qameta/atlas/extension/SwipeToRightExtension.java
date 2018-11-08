package io.qameta.atlas.extension;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
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

public class SwipeToRightExtension implements MethodExtension {

    private static final Duration SWIPE_DURATION = Duration.of(1000, ChronoUnit.MILLIS);

    @Override
    public boolean test(Method method) {
        return method.getName().equals("swipeToRight");
    }

    @Override
    public Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) throws Throwable {
        AppiumDriver driver = config.getContext(AppiumDriverContext.class).get().getValue();

        TouchAction action = new TouchAction(driver);
        Point location = ((WebElement) proxy).getLocation();
        Dimension size = ((WebElement) proxy).getSize();
        int xStart = (int) (size.width * 0.90);
        int y = location.getY() + size.height / 2;
        int xEnd = (int) (size.width * 0.05);
        action.press(new PointOption().withCoordinates(xStart, y)).waitAction(new WaitOptions()
                .withDuration(SWIPE_DURATION)).moveTo(new PointOption().withCoordinates(xEnd, y)).release().perform();
        return proxy;
    }
}



