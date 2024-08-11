plugins {
    `kotlin-android`
    com.android.application
}

android {
    namespace = "me.altered.platformer"
    compileSdk = 34

    sourceSets {
        named("main") {
            kotlin.srcDir("src")
            resources.srcDir("resources")
            res.srcDir("res")
            assets.srcDir("assets")
            manifest.srcFile("src/AndroidManifest.xml")
        }
    }
}

kotlin {
    jvmToolchain(17)
}

configurations.configureEach {
    exclude(group = "org.jetbrains.skiko", module = "skiko-awt")
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.skiko.android)
    runtimeOnly(libs.skiko.android.x64)
    runtimeOnly(libs.skiko.android.arm64)
}
