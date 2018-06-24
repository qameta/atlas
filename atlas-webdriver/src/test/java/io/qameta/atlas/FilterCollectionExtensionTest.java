package io.qameta.atlas;

import io.qameta.atlas.extension.FilterCollectionExtension;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.atlas.testdata.ObjectFactory.mockAtlasWebElement;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

/**
 * @author kurau (Yuri Kalinin)
 */
public class FilterCollectionExtensionTest {

    private static final boolean IS_DISPLAYED = true;
    private static final boolean NOT_DISPLAYED = false;

    private static final String TEXT = "text";
    private static final String WRONG_TEXT = "wrong";

    private ElementsCollection<AtlasWebElement> collection;
    private AtlasWebElement element = mockAtlasWebElement();

    @Test
    public void shouldReturnNewCollectionAfterFilter() {
        when(element.isDisplayed()).thenReturn(IS_DISPLAYED);
        collection = createElementsCollection(element);

        assertThat("Should return only displayed element", collection.filter(AtlasWebElement::isDisplayed), hasSize(1));
    }

    @Test
    public void shouldFilterElementsTwice() {
        when(element.isDisplayed()).thenReturn(IS_DISPLAYED);
        when(element.getText()).thenReturn(TEXT);
        collection = createElementsCollection(element);

        assertThat("Should filter the elements twice", collection.filter(AtlasWebElement::isDisplayed)
                .filter(e -> e.getText().contains(WRONG_TEXT)), hasSize(0));
    }

    @Test
    public void shouldReturnEmptyCollectionIf() {
        when(element.isDisplayed()).thenReturn(NOT_DISPLAYED);
        collection = createElementsCollection(element);

        assertThat("Should return empty list", collection.filter(AtlasWebElement::isDisplayed), hasSize(0));
    }

    @SuppressWarnings("unchecked")
    private static ElementsCollection<AtlasWebElement> createElementsCollection(AtlasWebElement... elements) {
        List target = new ArrayList();
        target.addAll(asList(elements));
        return new Atlas()
                .extension(new FilterCollectionExtension())
                .create(target, ElementsCollection.class);
    }
}