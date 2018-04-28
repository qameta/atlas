package io.qameta.atlas.extensions;

import io.qameta.atlas.Atlas;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class FindByExtension implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        assert proxy instanceof SearchContext;
        assert method.isAnnotationPresent(FindBy.class);

        String xpath = method.getAnnotation(FindBy.class).value();
        SearchContext context = (SearchContext) proxy;

        return new Atlas()
                .create(context.findElement(By.xpath(xpath)), method.getReturnType());
    }
}
