package io.qameta.atlas.webdriver;

import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.FindByCollectionExtension;
import io.qameta.atlas.webdriver.extension.FindByExtension;
import io.qameta.atlas.webdriver.extension.Param;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.webdriver.testdata.ObjectFactory.mockWebElement;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author kurau (Yuri Kalinin)
 */
public class FindByParameterizedTest {

    private WebElement parent = mockWebElement();

    private Atlas atlas;

    @Test
    public void shouldParameterizedFindBy() {
        when(parent.findElement(any(By.class))).thenReturn(mockWebElement());
        atlas = new Atlas().extension(new FindByExtension());

        String param = RandomStringUtils.randomAlphanumeric(10);

        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        atlasWebElement.childWithName(param).isDisplayed();

        verify(parent, times(1)).findElement(By.xpath(String.format("//div[%s]", param)));
    }

    @Test
    public void shouldParameterizedFindByCollection() {
        when(parent.findElements(any(By.class))).thenReturn(asList(mockWebElement()));
        atlas = new Atlas().extension(new FindByCollectionExtension());

        String param = RandomStringUtils.randomAlphanumeric(10);

        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        atlasWebElement.elements(param).size();

        verify(parent, times(1)).findElements(By.xpath(String.format("//td[%s]", param)));
    }

    interface ParentElement extends AtlasWebElement {

        @FindBy("//div[{{ value }}]")
        AtlasWebElement childWithName(@Param("value") String value);

        @FindBy("//td[{{ value }}]")
        ElementsCollection<AtlasWebElement> elements(@Param("value") String value);
    }
}
