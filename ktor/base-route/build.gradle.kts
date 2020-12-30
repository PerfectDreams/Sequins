plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "net.perfectdreams.sequins.ktor"
version = "1.0.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-core:1.5.0")
}

publishing {
    publications {
        register("PerfectDreams", MavenPublication::class.java) {
            from(components["java"])
        }
    }
}