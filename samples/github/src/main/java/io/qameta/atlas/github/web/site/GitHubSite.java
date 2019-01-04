package io.qameta.atlas.github.web.site;

import io.qameta.atlas.github.web.page.ContributorsPage;
import io.qameta.atlas.github.web.page.MainPage;
import io.qameta.atlas.github.web.page.ProjectPage;
import io.qameta.atlas.github.web.page.SearchPage;
import io.qameta.atlas.webdriver.WebSite;
import io.qameta.atlas.webdriver.extension.*;

/**
 * Point of testing WebSite.
 */
@BaseURI("https://github.com")
public interface GitHubSite extends WebSite {

    @Page
    MainPage onMainPage();

    @URL("search")
    SearchPage onSearchPage(@Query("q") String value);

    @URL("{profile}/{project}/tree/master/")
    ProjectPage onProjectPage(@Path("profile") String profile, @Path("project") String project);

    @Page
    ContributorsPage onContributorsPage();

}
