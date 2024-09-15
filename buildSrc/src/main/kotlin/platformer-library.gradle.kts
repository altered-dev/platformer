@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    `kotlin-multiplatform`
    `android-library`
}

android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    jvm()
    androidTarget()
    macosArm64()
    macosX64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    compilerOptions.freeCompilerArgs.addAll(
        "-Xexpect-actual-classes",
    )

    sourceSets {
        commonMain { platformerLayout() }
        commonTest { testPlatformerLayout() }
        jvmMain { platformerLayout("jvm") }
        jvmTest { testPlatformerLayout("jvm") }
        androidMain { platformerLayout("android") }
        macosMain {
            dependsOn(commonMain.get())
            platformerLayout("macos")
        }
        macosTest {
            dependsOn(commonTest.get())
            testPlatformerLayout("macos")
        }
        iosMain {
            dependsOn(commonMain.get())
            platformerLayout("ios")
        }
        iosTest {
            dependsOn(commonTest.get())
            testPlatformerLayout("ios")
        }
    }
}
