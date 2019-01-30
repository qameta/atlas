package io.qameta.atlas.webdriver.extension;

import io.qameta.atlas.core.AtlasException;
import io.qameta.atlas.core.api.MethodExtension;
import io.qameta.atlas.core.internal.Configuration;
import io.qameta.atlas.core.util.MethodInfo;
import io.qameta.atlas.webdriver.context.WebDriverContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

/**
 * JavaScript (executeScript) method extension.
 */
public class ExecuteJScriptMethodExtension implements MethodExtension {

    private static final String EXECUTE_SCRIPT = "executeScript";

    @Override
    public boolean test(final Method method) {
        return method.getName().equals(EXECUTE_SCRIPT);
    }

    @Override
    public Object invoke(final Object proxy,
                         final MethodInfo methodInfo,
                         final Configuration configuration) {
        final WebDriver driver = configuration.getContext(WebDriverContext.class)
                .orElseThrow(() -> new AtlasException("WebDriver is missing")).getValue();
        final String script = (String) methodInfo.getArgs()[0];
        final JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(script, proxy);
        return proxy;
    }
}
