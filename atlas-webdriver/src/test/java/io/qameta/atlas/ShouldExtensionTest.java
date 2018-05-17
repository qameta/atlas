package io.qameta.atlas;

import io.qameta.atlas.extensions.ShouldExtension;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static ru.yandex.qatools.matchers.webdriver.DisplayedMatcher.displayed;

/**
 * @author kurau (Yuri Kalinin)
 */
public class ShouldExtensionTest {

    private static final Matcher<WebElement> DISPLAYED_MATCHER = displayed();

    private static final boolean IS_DISPLAYED = true;
    private static final boolean NOT_DISPLAYED = false;

    private WebElement baseElement = mockWebElement();
    private AtlasWebElement atlasWebElement;
    private ElementsCollection<AtlasWebElement> collection;


    @Before
    public void createAtlasElementWithExtension() {
        atlasWebElement = new Atlas()
                .extension(new ShouldExtension())
                .create(baseElement, AtlasWebElement.class);
    }

    @Test
    public void shouldPassOneArgumentShouldMethod() {
        when(baseElement.isDisplayed()).thenReturn(IS_DISPLAYED);
        atlasWebElement.should(DISPLAYED_MATCHER);
    }

    @Test
    public void shouldPassTwoArgumentShouldMethod() {
        String message = RandomStringUtils.randomAlphanumeric(10);

        when(baseElement.isDisplayed()).thenReturn(IS_DISPLAYED);
        atlasWebElement.should(message, DISPLAYED_MATCHER);
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowAssertionErrorInOneArgumentShouldMethod() {
        when(baseElement.isDisplayed()).thenReturn(NOT_DISPLAYED);
        atlasWebElement.should(DISPLAYED_MATCHER);
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowAssertionErrorInTwoArgumentShouldMethod() {
        when(baseElement.isDisplayed()).thenReturn(NOT_DISPLAYED);
        atlasWebElement.should(DISPLAYED_MATCHER);
    }

    @Test
    public void shouldUseMethodForCollection() {
        collection = createElementsCollection(atlasWebElement);
        collection.should(hasSize(1));
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowAssertionErrorForCollection() {
        collection = createElementsCollection(atlasWebElement);
        collection.should(hasSize(0));
    }

    @Test
    public void shouldUseMethodForCollectionElements() {
        when(atlasWebElement.isDisplayed()).thenReturn(IS_DISPLAYED);
        collection = createElementsCollection(atlasWebElement);
        collection.should(hasItem(DISPLAYED_MATCHER));
    }

    @Test(expected = AssertionError.class)
    public void shouldThrowAssertionErrorForCollectionElements() {
        when(atlasWebElement.isDisplayed()).thenReturn(NOT_DISPLAYED);
        collection = createElementsCollection(atlasWebElement);
        collection.should(hasItem(DISPLAYED_MATCHER));
    }

    @SuppressWarnings("unchecked")
    private static ElementsCollection<AtlasWebElement> createElementsCollection(AtlasWebElement... elements) {
        List target = new ArrayList();
        target.addAll(asList(elements));
        return new Atlas()
                .extension(new ShouldExtension())
                .create(target, ElementsCollection.class);
    }
}
