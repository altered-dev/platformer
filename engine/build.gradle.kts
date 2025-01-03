plugins {
    `platformer-library`
}

android {
    namespace = "me.altered.platformer.engine"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.runtime)
            api(libs.androidx.annotation)
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.serialization.cbor)
            api(libs.kotlinx.serialization.json)
            api(libs.kotlinx.serialization.protobuf)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.kotlinx.coroutines.android)
        }
        named("desktopMain").dependencies {
            implementation(compose.desktop.common)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}
