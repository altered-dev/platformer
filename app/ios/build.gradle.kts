import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    platformer.`app-conventions`
}

fun KotlinNativeTarget.iosHost() {
    binaries {
        executable {
            entryPoint = "me.altered.platformer.ios.main"
            freeCompilerArgs += listOf(
                "-linker-option", "-framework", "-linker-option", "Metal",
                "-linker-option", "-framework", "-linker-option", "CoreText",
                "-linker-option", "-framework", "-linker-option", "CoreGraphics"
            )
        }
    }
}

kotlin {
    iosX64 { iosHost() }
    iosArm64 { iosHost() }

    sourceSets {
        commonMain {
            kotlin.srcDir("src")
            resources.srcDir("res")
            dependencies {
                implementation(project(":common"))
            }
        }
    }
}
