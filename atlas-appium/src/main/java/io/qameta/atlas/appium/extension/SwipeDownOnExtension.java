package io.qameta.atlas.appium.extension;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.appium.context.AppiumDriverContext;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import org.openqa.selenium.Dimension;

import java.lang.reflect.Method;

import static io.qameta.atlas.appium.Indent.BOTTOM;
import static io.qameta.atlas.appium.Indent.TOP;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;

/**
 * Swipe (Scroll) down to element.
 */
public class SwipeDownOnExtension implements MethodExtension {


    @Override
    public boolean test(final Method method) {
        return method.getName().equals("swipeDownOn");
    }

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration configuration) {
        final AppiumDriver driver = configuration.getContext(AppiumDriverContext.class)
                .orElseThrow(() -> new AtlasException("AppiumDriver is missing")).getValue();

        given().atMost(120, SECONDS).pollInterval(1, SECONDS).ignoreExceptions().until(() -> {
            if (!displayed().matches(proxy)) {
                final Dimension size = driver.manage().window().getSize();
                final int startX = size.width / 2;
                final int startY = (int) (size.height * BOTTOM.getValue());
                final int endX = size.width / 2;
                final int endY = (int) (size.height * TOP.getValue());

                final TouchAction touchAction = new TouchAction((PerformsTouchActions) driver);
                touchAction.longPress(new PointOption().withCoordinates(startX, startY))
                        .moveTo(new PointOption().withCoordinates(endX, endY)).release().perform();
            }
            return displayed().matches(proxy);
        });
        return proxy;
    }
}
