plugins {
    `platformer-library`
    `kotlinx-serialization`
}

android {
    namespace = "me.altered.platformer.engine"
    compileSdk = 34
}

dependencies {
    commonMainApi(project(":koml"))
    commonMainApi(libs.kotlinx.coroutines)
    commonMainApi(libs.kotlinx.serialization.cbor)
    commonMainApi(libs.kotlinx.serialization.json)
    commonMainApi(libs.skiko)
}
