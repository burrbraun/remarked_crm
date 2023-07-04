plugins {
    kotlin("jvm") version "1.8.0"
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


}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}
