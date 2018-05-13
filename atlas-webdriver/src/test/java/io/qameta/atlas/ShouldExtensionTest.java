package io.qameta.atlas;

import io.qameta.atlas.extensions.ShouldExtension;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
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
}
