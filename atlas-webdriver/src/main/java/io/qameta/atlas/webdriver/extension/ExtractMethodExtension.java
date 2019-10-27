package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.api.Target;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.target.HardcodedTarget;
import io.qameta.atlas.core.target.LazyTarget;
import io.qameta.atlas.core.util.MethodInfo;
import io.qameta.atlas.webdriver.ElementsCollection;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import static io.qameta.atlas.webdriver.util.MethodInfoUtils.getParamValues;
import static io.qameta.atlas.webdriver.util.MethodInfoUtils.processParamTemplate;
import static java.util.stream.Collectors.toList;

/**
 * @author artem.krosheninnikov
 */
public class ExtractMethodExtension implements MethodExtension {

    private static final String EXTRACT = "extract";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(EXTRACT)
                && List.class.isAssignableFrom(method.getReturnType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration config) {
        final Function converter = methodInfo.getParameter(Function.class).orElse(o -> o);

        final Method method = methodInfo.getMethod();

        final Map<String, String> parameters = getParamValues(method, methodInfo.getArgs());

//        final String name = Optional.ofNullable(method.getAnnotation(Name.class))
//                .map(Name::value)
//                .map(template -> processParamTemplate(template, parameters))
//                .orElse(method.getName());

//        final LazyTarget elementsTarget = new LazyTarget(name, () -> {
//            final List<String> originalElements = (List<String>) ((List) proxy)
//                    .stream()
//                    .map(converter)
//                    .collect(toList());
//
//            return originalElements,
//        }, converter);


        //return new Atlas(config.child()).create(elementsTarget, method.getReturnType());

        return new Atlas(config.child())
                .create(((List) proxy).stream().map(converter).collect(toList()), ElementsCollection.class);
    }

    private String listElementName(final String name, final int position) {
        return String.format("%s [%s]", name, position);
    }
}