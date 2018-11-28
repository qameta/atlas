package io.qameta.atlas.github.mobile.page;

import io.qameta.appium.AtlasMobileElement;
import io.qameta.appium.Screen;
import io.qameta.appium.annotations.AndroidFindBy;

public interface ArticleScreen extends Screen {

    @AndroidFindBy(id = "org.wikipedia:id/view_page_title_text")
    AtlasMobileElement articleTitle();
}
