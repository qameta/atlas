package io.qameta.atlas.webdriver.context;

import io.qameta.atlas.core.api.Context;
import io.qameta.atlas.webdriver.internal.URLBuilder;

/**
 * URLBuilder context.
 */
public class URLBuilderContext implements Context<URLBuilder> {

    private final URLBuilder urlBuilder;

    public URLBuilderContext(final URLBuilder urlBuilder) {
        this.urlBuilder = urlBuilder;
    }

    @Override
    public URLBuilder getValue() {
        return urlBuilder;
    }
}
