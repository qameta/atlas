package io.qameta.atlas.github.web.layout;

import io.qameta.atlas.extension.FindBy;
import io.qameta.atlas.github.web.element.Header;

public interface WithHeader {

    @FindBy("//header[contains(@class,'Header')]")
    Header header();

}