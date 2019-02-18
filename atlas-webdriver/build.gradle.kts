description = "Atlas Webdriver"

dependencies {
    api(project(":atlas-core"))

    implementation("org.seleniumhq.selenium:selenium-java")
    implementation("org.hamcrest:hamcrest-all")

    testImplementation("ru.yandex.qatools.matchers:webdriver-matchers")
    testImplementation("org.apache.commons:commons-lang3")
    testImplementation("org.mockito:mockito-all")
    testImplementation("org.assertj:assertj-core")
    testImplementation("junit:junit")
}
