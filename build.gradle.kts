plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "me.altered"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.lwjgl.bom))
    lwjgl(libs.bundles.lwjgl.withNatives)
    implementation(libs.bundles.skija)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.joml)
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

fun DependencyHandler.lwjgl(dependencyProvider: Provider<ExternalModuleDependencyBundle>) {
    val classifier = getClassifier(
        name = System.getProperty("os.name")!!,
        arch = System.getProperty("os.arch")!!,
    )
    implementation(dependencyProvider)
    dependencyProvider.get().forEach { dependency ->
        runtimeOnly(variantOf(provider { dependency }) { classifier(classifier) })
    }
}

fun getClassifier(name: String, arch: String) = when {
    name.startsWith("Linux") ||
    name.startsWith("SunOS") ||
    name.startsWith("Unit") -> when {
        arch.startsWith("arm") ||
        arch.startsWith("aarch64") -> when {
            "64" in arch || arch.startsWith("armv8") -> "natives-linux-arm64"
            else -> "natives-linux-arm32"
        }
        arch.startsWith("ppc") -> "natives-linux-ppc64le"
        arch.startsWith("riscv") -> "natives-linux-riscv64"
        else -> "natives-linux"
    }
    name.startsWith("Mac OS X") ||
    name.startsWith("Darwin") -> when {
        arch.startsWith("aarch64") -> "natives-macos-arm64"
        else -> "natives-macos"
    }
    name.startsWith("Windows") -> "natives-windows"
    else -> throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
}
