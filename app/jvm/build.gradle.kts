plugins {
    kotlin
}

kotlin {
    sourceSets {
        main { platformerLayout() }
        test { testPlatformerLayout() }
    }
}

dependencies {
    implementation(project(":shared"))
    runtimeOnly(libs.skiko.jvm.windows)
    runtimeOnly(libs.skiko.jvm.linux.x64)
    runtimeOnly(libs.skiko.jvm.linux.arm64)
    runtimeOnly(libs.skiko.jvm.macos.x64)
    runtimeOnly(libs.skiko.jvm.macos.arm64)
}
