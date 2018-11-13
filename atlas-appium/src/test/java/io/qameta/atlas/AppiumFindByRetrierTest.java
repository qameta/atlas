package io.qameta.atlas;

import io.appium.java_client.MobileElement;
import io.qameta.atlas.annotations.AndroidFindBy;
import io.qameta.atlas.api.Retry;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;

import static io.qameta.atlas.testdata.ObjectFactory.mockAppiumDriver;
import static io.qameta.atlas.testdata.ObjectFactory.mockAppiumElement;
import static org.mockito.Mockito.when;

public class AppiumFindByRetrierTest {

    @Test
    @Ignore
    public void retryChildFind() {
        MobileElement parentOrigin = mockAppiumElement();
        MobileElement childOrigin = mockAppiumElement();

        when(parentOrigin.findElementsByXPath("//div")).thenThrow(new NotFoundException());
        when(childOrigin.findElementsByXPath("//")).thenThrow(new NotFoundException());

        Atlas atlas = new Atlas(new AppiumDriverConfiguration(mockAppiumDriver()));
        ParentElement parent = atlas.create(parentOrigin, ParentElement.class);


        parent.child().isDisplayed();
    }

    interface ParentElement extends AtlasMobileElement {
        @Retry(timeout = 1000L)
        @AndroidFindBy(xpath = "//div")
        NestedElement child();
    }

    interface NestedElement extends AtlasMobileElement {

    }

}
