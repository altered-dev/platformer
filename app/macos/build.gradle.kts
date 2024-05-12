import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    platformer.`app-conventions`
}

fun KotlinNativeTarget.macosHost() {
    binaries {
        executable {
            entryPoint = "me.altered.platformer.macos.main"
            freeCompilerArgs += listOf(
                "-linker-option", "-framework", "-linker-option", "Metal",
                "-linker-option", "-framework", "-linker-option", "CoreText",
                "-linker-option", "-framework", "-linker-option", "CoreGraphics"
            )
        }
    }
}

kotlin {
    macosX64 { macosHost() }
    macosArm64 { macosHost() }

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
