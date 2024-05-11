plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "platformer"

include("common")
include("engine")
include("jvmApp")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
include("koml")
