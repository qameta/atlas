package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;

import java.lang.reflect.Method;

/**
 * Extension for methods with {@link DefaultSite} annotation.
 */
public class DefaultSiteExtension implements MethodExtension {

    @Override
    public boolean test(final Method method) {
        return method.isAnnotationPresent(DefaultSite.class);
    }

    @Override
    public Object invoke(final Object proxy, final MethodInfo methodInfo, final Configuration configuration) {
        final String baseIRU = (String) methodInfo.getArgs()[0];
        configuration.getStore().put("WEBSITE_URL", baseIRU);
        return proxy;
    }
}
