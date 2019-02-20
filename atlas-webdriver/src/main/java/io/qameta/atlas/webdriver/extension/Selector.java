package io.qameta.atlas.webdriver.extension;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;

/**
 * @author eroshenkoam (Artem Eroshenko).
 */
public enum Selector {

    CLASS_NAME {
        @Override
        public By buildBy(final String value) {
            return By.className(value);
        }
    },
    CSS {
        @Override
        public By buildBy(final String value) {
            return By.cssSelector(value);
        }
    },
    ID {
        @Override
        public By buildBy(final String value) {
            return By.id(value);
        }
    },
    ID_OR_NAME {
        @Override
        public By buildBy(final String value) {
            return new ByIdOrName(value);
        }
    },
    LINK_TEXT {
        @Override
        public By buildBy(final String value) {
            return By.linkText(value);
        }
    },
    NAME {
        @Override
        public By buildBy(final String value) {
            return By.name(value);
        }
    },
    PARTIAL_LINK_TEXT {
        @Override
        public By buildBy(final String value) {
            return By.partialLinkText(value);
        }
    },
    TAG_NAME {
        @Override
        public By buildBy(final String value) {
            return By.tagName(value);
        }
    },
    XPATH {
        @Override
        public By buildBy(final String value) {
            return By.xpath(value);
        }
    };

    public abstract By buildBy(String value);

}
