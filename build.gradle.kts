plugins {
    kotlin("multiplatform") version "1.6.20"
    `maven-publish`
}

group = "net.perfectdreams.sequins.ktor"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply<MavenPublishPlugin>()

    publishing {
        repositories {
            maven {
                name = "PerfectDreams"
                url = uri("https://repo.perfectdreams.net/")

                credentials(PasswordCredentials::class)
            }
        }
    }
}