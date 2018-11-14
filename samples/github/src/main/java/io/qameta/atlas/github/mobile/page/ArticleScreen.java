package io.qameta.atlas.github.mobile.page;

import io.qameta.atlas.AtlasMobileElement;
import io.qameta.atlas.Screen;
import io.qameta.atlas.annotations.AndroidFindBy;

public interface ArticleScreen extends Screen {

    @AndroidFindBy(id = "org.wikipedia:id/view_page_title_text")
    AtlasMobileElement articleTitle();
}
