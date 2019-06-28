package io.qameta.atlas.webdriver.internal;

import io.qameta.atlas.core.AtlasException;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Map;

public interface URLBuilder {


    /**
     * Build full completed URL.
     * @param objects - array of objects
     * @return {@link String}
     */
    String buildUrl(final Object[] objects);

    /**
     * Build full completed URL.
     *
     * @param baseURI         {@link String} - the base URI of WebSite.
     * @param pathSegment     {@link String} - the path segment.
     * @param queryParameters {@link Map} - the query parameters.
     * @return {@link String}
     */
    default String buildUrl(final String baseURI, final String pathSegment, final Map<String, String> queryParameters) {
        try {
            final URIBuilder urlBuilder;
            urlBuilder = new URIBuilder(baseURI);
            urlBuilder.setPath(pathSegment);
            queryParameters.forEach(urlBuilder::addParameter);
            return urlBuilder.toString();
        } catch (URISyntaxException exception) {
            throw new AtlasException("Can't parse base URL of your WebSite", exception);
        }
    }
}
