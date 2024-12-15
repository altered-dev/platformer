plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.plugin.android)
    implementation(libs.plugin.compose)
    implementation(libs.plugin.kotlin.compose)
    implementation(libs.plugin.kotlin.multiplatform)
    implementation(libs.plugin.kotlinx.serialization)
}
