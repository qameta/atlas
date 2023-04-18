import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

buildscript {
    repositories {
        jcenter()
        mavenLocal()
    }

}

val gradleScriptDir by extra("${rootProject.projectDir}/gradle")

tasks.withType(Wrapper::class) {
    gradleVersion = "7.5.1"
}

plugins {
    java
    signing
    `java-library`
    `maven-publish`
    id("ru.vyarus.quality") version "4.9.0"
    id("io.spring.dependency-management") version "1.1.0"
}

configure(listOf(rootProject)) {
    description = "Atlas"
    group = "io.qameta.atlas"
}

configure(subprojects) {
    group = "io.qameta.atlas"
    version = version

    apply(plugin = "java")
    apply(plugin = "signing")
    apply(plugin = "maven-publish")
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

    artifacts.add("archives", sourceJar)
    artifacts.add("archives", javadocJar)

    configure<DependencyManagementExtension> {
        dependencies {
            dependency("org.apache.commons:commons-lang3:3.12.0")

            dependency("org.seleniumhq.selenium:selenium-java:4.8.3")
            dependency("io.appium:java-client:8.3.0")
            dependency("io.github.bonigarcia:webdrivermanager:5.3.2")
            dependency("ru.yandex.qatools.matchers:webdriver-matchers:1.4.1")
            dependency("org.awaitility:awaitility:3.1.2")

            dependency("org.slf4j:slf4j-api:2.0.7")
            dependency("org.slf4j:slf4j-simple:2.0.7")

            dependency("org.hamcrest:hamcrest-all:1.3")
            dependency("org.assertj:assertj-core:3.24.2")
            dependency("org.mockito:mockito-core:4.8.0")
            dependency("junit:junit:4.13.2")
        }
    }

    repositories {
        jcenter()
        mavenLocal()
    }
}
