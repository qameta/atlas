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
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class SwipeToDownExtension implements MethodExtension {

    private static final Duration SWIPE_DURATION = Duration.of(1000, ChronoUnit.MILLIS);

    @Override
    public boolean test(Method method) {
        return method.getName().equals("swipeToDown");
    }

    @Override
    public Object invoke(Object proxy, MethodInfo methodInfo, Configuration config) throws Throwable {
        AppiumDriver driver = config.getContext(AppiumDriverContext.class).get().getValue();

        TouchAction action = new TouchAction(driver);
        Dimension size = ((WebElement) proxy).getSize();
        int xStart =  size.width / 2;
        int yStart = (int) (size.height * 0.80);
        int xEnd = size.width / 2;
        int yEnd = (int) (size.height * 0.20);
        action.press(new PointOption().withCoordinates(xStart, yStart)).waitAction(new WaitOptions()
                .withDuration(SWIPE_DURATION)).moveTo(new PointOption().withCoordinates(xEnd, yEnd)).release().perform();
        return proxy;
    }
}
