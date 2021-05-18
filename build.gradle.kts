plugins {
    java
    id("ru.vyarus.quality") version "4.0.0"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

description = "Atlas"

allprojects {
    group = "io.qameta.atlas"
    version = version
}

configure(subprojects) {
    apply(plugin = "java")
    apply(plugin = "maven")
    apply(plugin = "java-library")
    apply(plugin = "ru.vyarus.quality")
    apply(plugin = "io.spring.dependency-management")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    val sourceJar by tasks.creating(Jar::class) {
        from(sourceSets.getByName("main").allSource)
        archiveClassifier.set("sources")
    }

    val javadocJar by tasks.creating(Jar::class) {
        from(tasks.getByName("javadoc"))
        archiveClassifier.set("javadoc")
    }

    tasks.withType(Javadoc::class) {
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }

    dependencyManagement {
        dependencies {
            dependency("org.apache.commons:commons-lang3:3.7")

            dependency("org.seleniumhq.selenium:selenium-java:3.141.59")
            dependency("io.appium:java-client:6.1.0")
            dependency("io.github.bonigarcia:webdrivermanager:2.1.0")
            dependency("ru.yandex.qatools.matchers:webdriver-matchers:1.4.1")
            dependency("org.awaitility:awaitility:3.1.2")

            dependency("org.slf4j:slf4j-api:1.7.25")
            dependency("org.slf4j:slf4j-simple:1.7.25")

            dependency("org.hamcrest:hamcrest-all:1.3")
            dependency("org.assertj:assertj-core:3.6.2")
            dependency("org.mockito:mockito-core:3.2.4")
            dependency("junit:junit:4.12")
        }
    }

    repositories {
        jcenter()
        mavenLocal()
    }
}
