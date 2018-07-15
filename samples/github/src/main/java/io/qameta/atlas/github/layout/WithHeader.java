package io.qameta.atlas.github.layout;

import io.qameta.atlas.extension.FindBy;
import io.qameta.atlas.github.element.Header;

public interface WithHeader {

    @FindBy("//header[contains(@class,'Header')]")
    Header header();

}
