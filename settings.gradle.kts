rootProject.name = "platformer"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

include("common")
include("engine")
include("koml")
include("app")
include("app:jvm")
include("app:macos")
include("app:ios")
include(":app:android")
