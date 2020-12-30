pluginManagement {
    repositories {
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "Sequins"

include(":text-utils")
include(":ktor:base-route")