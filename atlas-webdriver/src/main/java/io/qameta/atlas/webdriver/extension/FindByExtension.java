package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.api.Target;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.target.LazyTarget;
import io.qameta.atlas.core.util.MethodInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import static io.qameta.atlas.webdriver.util.MethodInfoUtils.getParamValues;
import static io.qameta.atlas.webdriver.util.MethodInfoUtils.processParamTemplate;

/**
 * Extension for methods with {@link io.qameta.atlas.webdriver.extension.FindBy} annotation.
 */
public class FindByExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(FindBy.class)
                && WebElement.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        final Method method = methodInfo.getMethod();

        assert proxy instanceof SearchContext;
        assert method.isAnnotationPresent(FindBy.class);

        final Map<String, String> parameters = getParamValues(method, methodInfo.getArgs());
        final FindBy findBy = method.getAnnotation(FindBy.class);
        final By by = findBy.selector().buildBy(processParamTemplate(findBy.value(), parameters));

        final SearchContext searchContext = (SearchContext) proxy;
        final String name = Optional.ofNullable(method.getAnnotation(Name.class))
                .map(Name::value)
                .map(template -> processParamTemplate(template, parameters))
                .orElse(method.getName());

        final Configuration childConfiguration = configuration.child();
        final Target elementTarget = new LazyTarget(name, () -> searchContext.findElement(by));
        return new Atlas(childConfiguration)
                .create(elementTarget, method.getReturnType());
    }
}
