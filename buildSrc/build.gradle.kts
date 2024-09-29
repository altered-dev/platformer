plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.plugin)
    implementation(libs.compose.plugin)
    implementation(libs.compose.compiler.plugin)
    implementation(libs.kotlin.plugin)
    implementation(libs.serialization.plugin)
}

repositories {
    mavenCentral()
    google()
}
