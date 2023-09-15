plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.michael"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs += "-Xcontext-receivers"
    }
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}