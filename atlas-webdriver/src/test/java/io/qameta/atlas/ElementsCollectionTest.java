package io.qameta.atlas;

import io.qameta.atlas.api.Listener;
import io.qameta.atlas.extension.FindBy;
import io.qameta.atlas.extension.FindByCollectionExtension;
import io.qameta.atlas.internal.Configuration;
import io.qameta.atlas.util.MethodInfo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockAtlasWebElement;
import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ElementsCollectionTest {

    private static final String SELECTOR = "//div";
    private static final int DEFAULT_SIZE = 1;

    private WebElement parent;
    private ElementsCollection collection;

    @Before
    public void setUp() {
        parent = mockWebElement();
        collection = mock(ElementsCollection.class);
    }

    @Test
    public void shouldFindElementsCollection() {
        when(parent.findElements(By.xpath(SELECTOR))).thenReturn(collection);
        when(collection.size()).thenReturn(DEFAULT_SIZE);

        ParentElement parentElement = new Atlas()
                .extension(new FindByCollectionExtension())
                .create(parent, ParentElement.class);

        assertThat(parentElement.collection().size()).isEqualTo(DEFAULT_SIZE);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotInteractWithoutResult() {
        when(parent.findElements(By.xpath(SELECTOR))).thenReturn(null);
        when(collection.size()).thenReturn(DEFAULT_SIZE);

        ParentElement parentElement = new Atlas()
                .extension(new FindByCollectionExtension())
                .create(parent, ParentElement.class);

        assertThat(parentElement.collection().size()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    public void shouldFindNestedElementFromCollection() {
        AtlasWebElement listElement = mockAtlasWebElement();
        AtlasWebElement block = mockAtlasWebElement();

        when(parent.findElements(By.xpath(SELECTOR))).thenReturn(collection);
        when(collection.size()).thenReturn(DEFAULT_SIZE);
        when(collection.get(0)).thenReturn(listElement);

        when(listElement.findElement(By.xpath(SELECTOR))).thenReturn(block);
        when(block.isDisplayed()).thenReturn(true);

        ParentElement parentElement = new Atlas(new WebDriverConfiguration(mock(WebDriver.class)))
                .create(parent, ParentElement.class);

        ListElement element = parentElement.collection().get(0);
        assertThat(element.block().isDisplayed()).isEqualTo(true);
    }

    interface ParentElement extends AtlasWebElement {

        @FindBy(SELECTOR)
        ElementsCollection<ListElement> collection();

    }

    interface ListElement extends AtlasWebElement {

        @FindBy(SELECTOR)
        AtlasWebElement block();

    }
}
