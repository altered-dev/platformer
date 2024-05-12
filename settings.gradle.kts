plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "platformer"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include("common")
include("engine")
include("koml")
include("app")
include("app:jvm")
include("app:macos")
include("app:ios")
findProject(":app:ios")?.name = "ios"
