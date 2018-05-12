package io.qameta.atlas.matcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * WebDriver displayed matcher.
 */
public final class DisplayedMatcher extends TypeSafeMatcher<WebElement> {

    private DisplayedMatcher() {
    }

    @Override
    protected boolean matchesSafely(final WebElement element) {
        try {
            return element.isDisplayed();
        } catch (WebDriverException e) {
            return false;
        }
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("element is displayed");
    }

    @Override
    public void describeMismatchSafely(final WebElement element, final Description mismatchDescription) {
        mismatchDescription.appendText("element ").appendValue(element).appendText(" is not displayed");
    }

    /**
     * Creates matcher that checks if element is currently displayed on page.
     */
    @Factory
    public static Matcher<WebElement> displayed() {
        return new DisplayedMatcher();
    }
}
