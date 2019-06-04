package io.qameta.atlas.core.target;

import io.qameta.atlas.core.api.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Artem Eroshenko.
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class LazyTarget implements Target {

    private final String name;

    private final Supplier<Object> supplier;

    private final List<Function> extractor;

    public LazyTarget(final String name, final Supplier<Object> supplier) {
        this(name, supplier, r -> r);
    }

    public LazyTarget(final String name, final Supplier<Object> supplier, final Function extractor) {
        this.supplier = supplier;
        this.name = name;
        this.extractor = new ArrayList<>();
        this.extractor.add(extractor);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object instance() {
        return supplier.get();
    }

    public List<Function> extractors() {
        return extractor;
    }
}
