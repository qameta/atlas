package io.qameta.atlas.util;

import org.hamcrest.Matcher;

/**
 * @author kurau (Yuri Kalinin)
 */
public final class MethodInfoUtils {

    private static final String UNEXPECTED_METHOD_SIGNATURE = "Unexpected method signature";

    private static final int ONLY_ONE_PARAM = 1;
    private static final int TWO_PARAMS = 2;

    private MethodInfoUtils() {
    }

    public static String getMessage(final Object... args) {
        if (args.length == ONLY_ONE_PARAM) {
            return "";
        } else if (args.length == TWO_PARAMS) {
            return (String) args[0];
        } else {
            throw new IllegalStateException(UNEXPECTED_METHOD_SIGNATURE);
        }
    }

    public static Matcher getMatcher(final Object... args) {
        if (args.length == ONLY_ONE_PARAM) {
            return (Matcher) args[0];
        } else if (args.length == TWO_PARAMS) {
            return (Matcher) args[1];
        } else {
            throw new IllegalStateException(UNEXPECTED_METHOD_SIGNATURE);
        }
    }
}
