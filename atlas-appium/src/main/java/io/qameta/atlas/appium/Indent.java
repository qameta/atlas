package io.qameta.atlas.appium;

/**
 * Indent for swipe(scroll).
 */
public enum Indent {
    BOTTOM(0.80), TOP(0.20), LEFT(0.30), RIGHT(0.70);

    private final double value;

    Indent(final double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
