rootProject.name = "platformer"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(
    ":engine",
    ":composeApp",
    ":common",
    ":scene:editor",
    ":scene:level",
    ":scene:main",
    ":scene:settings",
    ":scene:shop",
    ":scene:workshop",
)
