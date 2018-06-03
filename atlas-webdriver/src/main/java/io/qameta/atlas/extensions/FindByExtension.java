package io.qameta.atlas.extensions;

import io.qameta.atlas.Atlas;
import io.qameta.atlas.api.MethodExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Extension for methods with {@link io.qameta.atlas.extensions.FindBy} annotation.
 */
public class FindByExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(FindBy.class)
                && WebElement.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) {
        assert proxy instanceof SearchContext;
        assert method.isAnnotationPresent(FindBy.class);

        final String xpath = method.getAnnotation(FindBy.class).value();
        final String name = Optional.ofNullable(method.getAnnotation(Name.class)).map(Name::value)
                .orElse(method.getName());
        final SearchContext context = (SearchContext) proxy;

        return new Atlas()
                .extension(new ToStringMethodExtension(name))
                .create(context.findElement(By.xpath(xpath)), method.getReturnType());
    }
}
