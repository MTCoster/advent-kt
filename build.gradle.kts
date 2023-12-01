plugins {
    kotlin("jvm") version "1.9.21"
}

group = "net.mtcoster"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useTestNG()
}

kotlin {
    jvmToolchain(21)
}
