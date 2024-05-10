plugins {
    platformer.`mp-conventions`
}

dependencies {
    commonMainApi(libs.skiko)
    commonMainApi(libs.kotlinx.coroutines)
    commonTestImplementation(kotlin("test"))

    jvmMainApi(platform(libs.lwjgl.bom))
    jvmMainApi(libs.joml)
    jvmMainApi(libs.bundles.skiko.jvm)
    lwjgl(libs.bundles.lwjgl.withNatives)

    iosArm64MainApi(libs.skiko.ios.arm64)
    macosArm64MainApi(libs.skiko.macos.arm64)
}

fun DependencyHandler.lwjgl(dependencyProvider: Provider<ExternalModuleDependencyBundle>) {
    val classifier = getClassifier(
        name = System.getProperty("os.name")!!,
        arch = System.getProperty("os.arch")!!,
//        name = "Linux",
//        arch = "",
    )
    jvmMainApi(dependencyProvider)
    dependencyProvider.get().forEach { dependency ->
        jvmMainRuntimeOnly(variantOf(provider { dependency }) { classifier(classifier) })
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
