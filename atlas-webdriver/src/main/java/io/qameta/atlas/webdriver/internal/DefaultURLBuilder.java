package io.qameta.atlas.webdriver.internal;

import java.util.Map;

/**
 * The default implementation of URLBuilder.
 */
public class DefaultURLBuilder implements URLBuilder {

    @SuppressWarnings("unchecked")
    @Override
    public String buildUrl(Object[] args) {
        return buildUrl((String) args[0], (String) args[1], (Map<String, String>) args[2]);
    }
}
