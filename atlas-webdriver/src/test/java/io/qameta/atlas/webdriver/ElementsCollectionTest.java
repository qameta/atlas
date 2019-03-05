package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.internal.DefaultRetryer;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.FindByCollectionExtension;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockAtlasWebElement;
import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ElementsCollectionTest {

    private static final String SELECTOR = "//div";
    private static final int DEFAULT_SIZE = 1;

    private WebElement parent;
    private ElementsCollection collection;
    private DefaultRetryer defaultRetryer;

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
                .context(defaultRetryer)
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

    @Test
    public void shouldRefreshCollectionWhenWaiting() {
        WebElement listElement = mockWebElement();
        final AtomicInteger count = new AtomicInteger();
        when(parent.findElements(By.xpath(SELECTOR))).then((Answer<List<WebElement>>) (invocation) -> {
            if (count.incrementAndGet() > 3) {
                return Collections.singletonList(listElement);
            }
            return new ArrayList<>();
        });
        ParentElement parentElement = new Atlas(new WebDriverConfiguration(mock(WebDriver.class)))
                .create(parent, ParentElement.class);
        parentElement.collection()
                .should(hasSize(1));
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
