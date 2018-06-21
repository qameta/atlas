package io.qameta.atlas.extension;

import io.qameta.atlas.Atlas;
import io.qameta.atlas.api.MethodExtension;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Extension for methods with {@link io.qameta.atlas.extension.FindBy} annotation
 * and {@link io.qameta.atlas.ElementsCollection} return type.
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

        final String xpath = method.getAnnotation(FindBy.class).value();
        final String name = Optional.ofNullable(method.getAnnotation(Name.class)).map(Name::value)
                .orElse(method.getName());
        final SearchContext context = (SearchContext) proxy;

        final List<WebElement> originalElements = context.findElements(By.xpath(xpath));
        final Type methodReturnType = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];

        final List newElements = IntStream.range(0, originalElements.size())
                .mapToObj(i -> new Atlas().extension(new ToStringMethodExtension(listElementName(name, i)))
                        .create(originalElements.get(i), (Class<?>) methodReturnType))
                .collect(toList());

        return new Atlas(configuration)
                .create(newElements, method.getReturnType());
    }

    private String listElementName(final String name, final int position) {
        return String.format("%s [%s]", name, position);
    }
}
