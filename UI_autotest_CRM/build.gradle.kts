plugins {
    kotlin("jvm") version "1.8.0"
    id ("io.qameta.allure") version "2.9.6"
    application

}


group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("com.codeborne:selenide:6.13.1")
    testImplementation(kotlin("test"))
    implementation ("commons-io:commons-io:2.11.0")
    implementation ("mysql:mysql-connector-java:8.0.19")
    implementation ("com.opencsv:opencsv:5.3")
    testImplementation("org.testng:testng:7.1.0")
    implementation ("org.selenide:selenide-selenoid:1.1.3")
    testImplementation("org.aspectj:aspectjweaver:1.9.5")
    testImplementation("io.qameta.allure:allure-testng:2.12.1")
    testImplementation("io.qameta.allure:allure-commandline:2.23.1")
    testImplementation("io.qameta.allure:allure-assertj:2.12.1")
    testImplementation("io.qameta.allure:allure-rest-assured:2.12.1")
    testImplementation("io.qameta.allure:allure-java-commons:2.12.1")
}

kotlin {
    jvmToolchain(11)
}

val suite1 = project.hasProperty("suiteReportsTest")
val suite2 = project.hasProperty("suiteSQLTests")

tasks.test {
    useTestNG {

                useDefaultListeners = true

        if (suite1) {
            suites ("src/test/resources/suiteReportsTest.xml")
        }
        if (suite2) {
            suites ("src/test/resources/suiteSQLTests.xml")
        }
    }
}

