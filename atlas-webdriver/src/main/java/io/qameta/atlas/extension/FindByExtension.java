package io.qameta.atlas.extension;

import io.qameta.atlas.Atlas;
import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.api.Target;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.target.HardcodedTarget;
import io.qameta.atlas.util.MethodInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import static io.qameta.atlas.util.MethodInfoUtils.getParameters;
import static io.qameta.atlas.util.MethodInfoUtils.processTemplate;

/**
 * Extension for methods with {@link io.qameta.atlas.extension.FindBy} annotation.
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

        final Map<String, String> parameters = getParameters(method, methodInfo.getArgs());
        final String xpath = processTemplate(method.getAnnotation(FindBy.class).value(), parameters);

        final SearchContext searchContext = (SearchContext) proxy;
        final String name = Optional.ofNullable(method.getAnnotation(Name.class)).map(Name::value)
                .orElse(method.getName());

        final Configuration childConfiguration = configuration.child();
        final Target target = new HardcodedTarget(name, searchContext.findElement(By.xpath(xpath)));
        return new Atlas(childConfiguration)
                .create(target, method.getReturnType());
    }
}
