package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.extension.ExtractMethodExtension;
import io.qameta.atlas.webdriver.extension.FindBy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockAtlasWebElement;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author artem.krosheninnikov
 */
public class ExtractCollectionExtensionTest {

    private ElementsCollection<AtlasWebElement> collection;
    private AtlasWebElement element = mockAtlasWebElement();

    @Test
    public void shouldExtractElementsCollectionToList() {
        when(element.getText()).thenReturn("mytext");
        collection = createElements(element);

        assertThat(collection.extract(AtlasWebElement::getText).get(0)).contains(element.getText());
    }

    @SuppressWarnings("unchecked")
    private static ElementsCollection<AtlasWebElement> createElements(AtlasWebElement... elements) {
        List target = new ArrayList(asList(elements));
        return new Atlas()
                .extension(new ExtractMethodExtension())
                .create(target, ElementsCollection.class);
    }

    interface StatusItem extends AtlasWebElement<StatusItem> {

        @FindBy(".//div")
        AtlasWebElement title();

    }

    interface ParentElement extends AtlasWebElement {
        @FindBy("//")
        ElementsCollection<StatusItem> items();
    }


}
