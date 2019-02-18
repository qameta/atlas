import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

buildscript {
    repositories {
        jcenter()
        mavenLocal()
    }

    dependencies {
        classpath("ru.vyarus:gradle-quality-plugin:2.3.0")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.0")
        classpath("io.spring.gradle:dependency-management-plugin:1.0.0.RELEASE")
        classpath("net.researchgate:gradle-release:2.7.0")
    }
}

tasks.withType(Wrapper::class) {
    gradleVersion = "5.2.1"
}

val gradleScriptDir by extra("${rootProject.projectDir}/gradle")

plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

configure(listOf(rootProject)) {
    description = "Atlas"
    group = "io.qameta.atlas"
}

configure(subprojects) {
    val project = this
    group = "io.qameta.atlas"
    version = version

    apply(plugin = "java")
    apply(plugin = "maven")
    apply(plugin = "java-library")
    apply(plugin = "ru.vyarus.quality")
    apply(plugin = "io.spring.dependency-management")

    apply(from = "$gradleScriptDir/bintray.gradle")
    apply(from = "$gradleScriptDir/maven.gradle")

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    configure<DependencyManagementExtension> {
        dependencies {
            dependency("org.apache.commons:commons-lang3:3.7")

            dependency("org.seleniumhq.selenium:selenium-java:3.8.1")
            dependency("io.appium:java-client:6.1.0")
            dependency("io.github.bonigarcia:webdrivermanager:2.1.0")
            dependency("ru.yandex.qatools.matchers:webdriver-matchers:1.4.1")
            dependency("org.awaitility:awaitility:3.1.2")

            dependency("org.slf4j:slf4j-api:1.7.25")
            dependency("org.slf4j:slf4j-simple:1.7.25")

            dependency("org.hamcrest:hamcrest-all:1.3")
            dependency("org.assertj:assertj-core:3.6.2")
            dependency("org.mockito:mockito-all:1.10.19")
            dependency("junit:junit:4.12")
        }
    }

    repositories {
        jcenter()
        mavenLocal()
    }
}
