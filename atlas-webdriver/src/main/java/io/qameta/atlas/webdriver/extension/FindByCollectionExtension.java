package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.api.Target;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.target.HardcodedTarget;
import io.qameta.atlas.core.target.LazyTarget;
import io.qameta.atlas.core.util.MethodInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static io.qameta.atlas.webdriver.util.MethodInfoUtils.getParamValues;
import static io.qameta.atlas.webdriver.util.MethodInfoUtils.processParamTemplate;
import static java.util.stream.Collectors.toList;

/**
 * Extension for methods with {@link io.qameta.atlas.webdriver.extension.FindBy} annotation
 * and {@link io.qameta.atlas.webdriver.ElementsCollection} return type.
 */
public class FindByCollectionExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(FindBy.class)
                && List.class.isAssignableFrom(method.getReturnType());
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

        final String name = Optional.ofNullable(method.getAnnotation(Name.class))
                .map(Name::value)
                .map(template -> processParamTemplate(template, parameters))
                .orElse(method.getName());
        final SearchContext context = (SearchContext) proxy;

        final LazyTarget elementsTarget = new LazyTarget(name, () -> {
            final List<WebElement> originalElements = context.findElements(by);
            final Type methodReturnType = ((ParameterizedType) method.getGenericReturnType())
                    .getActualTypeArguments()[0];

            return IntStream.range(0, originalElements.size())
                    .mapToObj(i -> {
                        final WebElement originalElement = originalElements.get(i);
                        final Configuration childConfiguration = configuration.child();
                        final Target target = new HardcodedTarget(listElementName(name, i), originalElement);
                        return new Atlas(childConfiguration)
                                .create(target, (Class<?>) methodReturnType);
                    })
                    .collect(toList());
        });

        return new Atlas(configuration.child())
                .create(elementsTarget, method.getReturnType());
    }

    private String listElementName(final String name, final int position) {
        return String.format("%s [%s]", name, position);
    }
}
