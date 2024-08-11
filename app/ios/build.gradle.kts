plugins {
    `kotlin-multiplatform`
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.executable()
    }

    sourceSets {
        commonMain { platformerLayout() }
        commonTest { testPlatformerLayout() }
    }
}

dependencies {
    commonMainImplementation(project(":shared"))
}
