plugins {
    `platformer-library`
    `kotlinx-serialization`
}

android {
    namespace = "me.altered.platformer"
    compileSdk = 34
}

dependencies {
    commonMainApi(project(":engine"))
    commonTestImplementation(kotlin("test"))
    jvmTestImplementation(libs.junit.jupiter)
}
