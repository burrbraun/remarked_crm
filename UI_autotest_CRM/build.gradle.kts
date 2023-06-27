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
    implementation ("org.junit.jupiter:junit-jupiter:5.9.0")
    implementation ("org.junit.jupiter:junit-jupiter-api:5.9.0")
    implementation ("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation(kotlin("test"))
    implementation ("commons-io:commons-io:2.11.0")
    implementation ("mysql:mysql-connector-java:8.0.19")
    implementation ("com.opencsv:opencsv:5.3")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}