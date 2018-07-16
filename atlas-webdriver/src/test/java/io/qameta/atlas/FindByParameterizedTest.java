package io.qameta.atlas;

import io.qameta.atlas.extension.FindBy;
import io.qameta.atlas.extension.FindByCollectionExtension;
import io.qameta.atlas.extension.FindByExtension;
import io.qameta.atlas.extension.Param;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
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
        when(parent.findElement(any())).thenReturn(mockWebElement());
        atlas = new Atlas().extension(new FindByExtension());

        String param = RandomStringUtils.randomAlphanumeric(10);

        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        atlasWebElement.childWithName(param).isDisplayed();

        verify(parent, times(1)).findElement(By.xpath(String.format("//div[%s]", param)));
    }

    @Test
    public void shouldParameterizedFindByCollection() {
        when(parent.findElements(any())).thenReturn(asList(mockWebElement()));
        atlas = new Atlas().extension(new FindByCollectionExtension());

        String param = RandomStringUtils.randomAlphanumeric(10);

        ParentElement atlasWebElement = atlas.create(parent, ParentElement.class);
        atlasWebElement.elements(param);

        verify(parent, times(1)).findElements(By.xpath(String.format("//td[%s]", param)));
    }

    interface ParentElement extends AtlasWebElement {

        @FindBy("//div[{{ value }}]")
        AtlasWebElement childWithName(@Param("value") String value);

        @FindBy("//td[{{ value }}]")
        ElementsCollection<AtlasWebElement> elements(@Param("value") String value);
    }
}
