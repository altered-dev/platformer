@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    android
    org.jetbrains.compose
    `kotlin-composecompiler`
    `kotlin-multiplatform`
    `kotlinx-serialization`
}

kotlin {
    jvmToolchain(17)

    androidTarget()
    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "platformer"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.io.core)
            implementation(projects.common)
            implementation(projects.scene.editor)
            implementation(projects.scene.level)
            implementation(projects.scene.main)
            implementation(projects.scene.settings)
            implementation(projects.scene.shop)
            implementation(projects.scene.workshop)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        named("desktopMain").dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }

    compilerOptions {
        optIn.addAll(
            "kotlinx.serialization.ExperimentalSerializationApi"
        )
    }
}

android {
    namespace = "me.altered.platformer"
    compileSdk = 34

    sourceSets.named("main") {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
        resources.srcDirs("src/commonMain/resources")
    }

    defaultConfig {
        applicationId = "me.altered.platformer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
}

compose.desktop {
    application {
        mainClass = "me.altered.platformer.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "me.altered.platformer"
            packageVersion = "1.0.0"
        }
    }
}

compose.resources {
    packageOfResClass = "me.altered.platformer"
}
