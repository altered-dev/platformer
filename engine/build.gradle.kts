plugins {
    platformer.`mp-conventions`
}

dependencies {
    commonMainApi(libs.skiko)
    commonMainApi(libs.kotlinx.coroutines)
    commonMainApi(project(":koml"))
    commonTestImplementation(kotlin("test"))

    jvmMainApi(platform(libs.lwjgl.bom))
    jvmMainApi(libs.bundles.skiko.jvm)

    iosArm64MainApi(libs.skiko.ios.arm64)
    macosArm64MainApi(libs.skiko.macos.arm64)
}
