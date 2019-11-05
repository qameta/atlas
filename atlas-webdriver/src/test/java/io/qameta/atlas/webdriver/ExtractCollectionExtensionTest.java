package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.extension.ExtractMethodExtension;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.FindByCollectionExtension;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockAtlasWebElement;
import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author artem.krosheninnikov
 */
public class ExtractCollectionExtensionTest {

    private static final String SELECTOR = "//div";
    private static final int DEFAULT_SIZE = 1;


    private ElementsCollection<AtlasWebElement> collection;
    private AtlasWebElement element = mockAtlasWebElement();

    @Test
    public void shouldExtractElementsCollectionToList() {
        when(element.getText()).thenReturn("mytext");
        collection = createElements(element);

        assertThat(collection.extract(AtlasWebElement::getText)).contains(element.getText());
    }

    @Test
    public void shouldExtractTwoTimesInARow() {
        WebElement parent = mockWebElement();
        ElementsCollection collection = mock(ElementsCollection.class);
        StatusItem status = mock(StatusItem.class, withSettings());
        AtlasWebElement innerElement = mockAtlasWebElement();

        when(parent.getText()).thenReturn("text");
        when(parent.findElements(By.xpath(SELECTOR))).thenReturn(collection);
        when(collection.size()).thenReturn(DEFAULT_SIZE);
        when(collection.get(0)).thenReturn(status);
        when(status.title()).thenReturn(innerElement);
        when(innerElement.getText()).thenReturn("innerText");

        ParentElement parentElement = new Atlas()
                .extension(new ExtractMethodExtension())
                .extension(new FindByCollectionExtension())
                .create(parent, ParentElement.class);

        assertThat(parentElement.items().extract(StatusItem::title).extract(AtlasWebElement::getText)).contains("innerText");
    }

    @SuppressWarnings("unchecked")
    private static ElementsCollection<AtlasWebElement> createElements(AtlasWebElement... elements) {
        List target = new ArrayList(asList(elements));
        return new Atlas()
                .extension(new ExtractMethodExtension())
                .create(target, ElementsCollection.class);
    }

    interface StatusItem extends AtlasWebElement {

        @FindBy(SELECTOR)
        AtlasWebElement title();

    }

    interface ParentElement extends AtlasWebElement {

        @FindBy(SELECTOR)
        ElementsCollection<StatusItem> items();
    }


}
