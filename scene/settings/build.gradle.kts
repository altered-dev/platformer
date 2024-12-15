plugins {
    `platformer-library`
}

android {
    namespace = "me.altered.platformer.scene.settings"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.common)
            implementation(compose.components.resources)
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
