dependencies {
    implementation(project("src:main"))
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs += "-Xcontext-receivers"
    }
}
