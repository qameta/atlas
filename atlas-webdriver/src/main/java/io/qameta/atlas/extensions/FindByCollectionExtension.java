package io.qameta.atlas.extensions;

import io.qameta.atlas.Atlas;
import io.qameta.atlas.api.Extension;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * FindBy Extension for collection.
 */
public class FindByCollectionExtension implements Extension {

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(FindBy.class)
                && List.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) {
        assert proxy instanceof SearchContext;
        assert method.isAnnotationPresent(FindBy.class);

        final String xpath = method.getAnnotation(FindBy.class).value();
        final SearchContext context = (SearchContext) proxy;

        final List<WebElement> originalElements = context.findElements(By.xpath(xpath));
        final Type methodReturnType = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];

        final List newElements = IntStream.range(0, originalElements.size())
                .mapToObj(i -> new Atlas().create(originalElements.get(i), (Class<?>) methodReturnType))
                .collect(toList());

        return new Atlas().create(newElements, method.getReturnType());
    }
}
