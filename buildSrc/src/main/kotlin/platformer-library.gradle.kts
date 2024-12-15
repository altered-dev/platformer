@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    `android-library`
    org.jetbrains.compose
    `kotlin-composecompiler`
    `kotlin-multiplatform`
    `kotlinx-serialization`
}

kotlin {
    jvmToolchain(17)

    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    compilerOptions {
        optIn.addAll(
            "kotlinx.serialization.ExperimentalSerializationApi"
        )
    }
}

android {
    compileSdk = 34

    sourceSets.named("main") {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
        resources.srcDirs("src/commonMain/resources")
    }
}
