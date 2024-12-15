plugins {
    `platformer-library`
}

android {
    namespace = "me.altered.platformer.common"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(compose.components.resources)
            api(libs.androidx.annotation)
            api(libs.androidx.navigation.compose)
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.serialization.cbor)
            api(libs.kotlinx.serialization.json)
            api(libs.kotlinx.serialization.protobuf)
            api(projects.engine)

            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.kotlinx.io.core)
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

compose.resources {
    publicResClass = true
    packageOfResClass = "me.altered.platformer.resources"
    generateResClass = always
}
