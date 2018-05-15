package io.qameta.atlas.extensions;

import io.qameta.atlas.Atlas;
import io.qameta.atlas.api.Extension;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import java.lang.reflect.Method;
import java.util.List;

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

        return new Atlas()
                .create(context.findElements(By.xpath(xpath)), method.getReturnType());
    }
}
