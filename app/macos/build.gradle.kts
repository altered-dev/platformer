plugins {
    `kotlin-multiplatform`
}

kotlin {
    listOf(
        macosX64(),
        macosArm64(),
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
