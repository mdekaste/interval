plugins {
    kotlin("jvm") version "2.0.0-Beta3"
    application
}

group = "org.michael"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
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
