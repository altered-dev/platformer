plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.plugin)
    implementation(libs.kotlin.plugin)
    implementation(libs.serialization.plugin)
}

repositories {
    mavenCentral()
    google()
}
