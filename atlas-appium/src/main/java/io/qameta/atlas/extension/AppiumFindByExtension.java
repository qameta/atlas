package io.qameta.atlas.extension;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.qameta.atlas.Atlas;
import io.qameta.atlas.AtlasException;
import io.qameta.atlas.annotations.AndroidFindBy;
import io.qameta.atlas.annotations.IOSFindBy;
import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.api.Target;
import io.qameta.atlas.context.AppiumDriverContext;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.target.LazyTarget;
import io.qameta.atlas.util.MethodInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static io.qameta.atlas.extension.AppiumFindByExtension.TypeLocator.ID;
import static io.qameta.atlas.extension.AppiumFindByExtension.TypeLocator.XPATH;
import static io.qameta.atlas.util.MethodInfoUtils.getParameters;
import static io.qameta.atlas.util.MethodInfoUtils.processTemplate;

/**
 * FindBy for both platfrom (Android&IOS)
 */
public class AppiumFindByExtension implements MethodExtension {

    @Override
    public boolean test(Method method) {
        return method.isAnnotationPresent(IOSFindBy.class)
                || method.isAnnotationPresent(AndroidFindBy.class)
                && WebElement.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    public Object invoke(Object proxy, MethodInfo methodInfo, Configuration configuration) throws Throwable {
        final Method method = methodInfo.getMethod();
        final boolean annotationsPresent = Stream.of(method.getDeclaredAnnotations())
                .anyMatch(it -> it instanceof IOSFindBy || it instanceof AndroidFindBy);

        assert proxy instanceof SearchContext;
        assert proxy instanceof WrapsDriver;
        assert annotationsPresent;

        final Map<String, String> parameters = getParameters(method, methodInfo.getArgs());
        final WrapsDriver searchContext = (WrapsDriver) proxy;
        final Configuration childConfiguration = configuration.child();
        final String name = Optional.ofNullable(method.getAnnotation(Name.class))
                .map(Name::value)
                .orElse(method.getName());
        final AppiumDriver driver = configuration.getContext(AppiumDriverContext.class).get().getValue(); //TODO:refactor

        final By locator;
        if (driver instanceof AndroidDriver) {
            final String xpath = processTemplate(method.getAnnotation(AndroidFindBy.class).xpath(), parameters);
            final String id = processTemplate(method.getAnnotation(AndroidFindBy.class).id(), parameters);
            locator = getByLocator(new LocatorWrapper(XPATH, xpath), new LocatorWrapper(ID, id));

        } else if (driver instanceof IOSDriver) {
            final String xpath = processTemplate(method.getAnnotation(IOSFindBy.class).xpath(), parameters);
            final String id = processTemplate(method.getAnnotation(IOSFindBy.class).id(), parameters);
            locator = getByLocator(new LocatorWrapper(XPATH, xpath), new LocatorWrapper(ID, id));
        } else {
            throw new AtlasException("Сan not identified driver");
        }

        final Target target = new LazyTarget(name,
                () -> searchContext.getWrappedDriver().findElement(locator));
        return new Atlas(childConfiguration).create(target, method.getReturnType());
    }

    private By getByLocator(LocatorWrapper ... locatorWrappers) {
        return Stream.of(locatorWrappers)
                .filter(LocatorWrapper::isNotEmptyLocator)
                .map(LocatorWrapper::toBy)
                .findFirst()
                .orElseThrow(() -> new AtlasException("No valid locators found"));
    }

    enum TypeLocator {
        ID, XPATH;
    }

    private final class LocatorWrapper {
        private TypeLocator type;
        private String locator;

        private LocatorWrapper(final TypeLocator type, final String locator) {
            this.type = type;
            this.locator = locator;
        }

        private boolean isNotEmptyLocator() {
            return !this.locator.isEmpty();
        }

        private By toBy() {
            if (XPATH == type) {
                return By.xpath(locator);
            } else if (ID == type) {
                return By.id(locator);
            } else {
                throw new UnsupportedOperationException("Unsupported locator type");
            }
        }
    }
}


