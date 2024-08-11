rootProject.name = "platformer"

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
    ":app:android",
    ":app:ios",
    ":app:jvm",
    ":app:macos",
    ":engine",
    ":koml",
    ":shared"
)
