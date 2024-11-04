plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvmToolchain(17)

    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.runtime)
            api(libs.androidx.annotation)
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.serialization.cbor)
            api(libs.kotlinx.serialization.json)
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        desktopMain.dependencies {
            implementation(compose.desktop.common)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "me.altered.platformer.engine"
    compileSdk = 34

    buildFeatures {
        compose = true
    }
}

compose.resources {
    packageOfResClass = "me.altered.platformer.engine"
}
