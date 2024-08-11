import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun KotlinSourceSet.platformerLayout() {
    kotlin.srcDir("src")
    resources.srcDir("resources")
}

fun KotlinSourceSet.testPlatformerLayout() {
    kotlin.srcDir("test")
    resources.srcDir("testResources")
}

fun KotlinSourceSet.platformerLayout(platform: String) {
    kotlin.srcDir("src@$platform")
    resources.srcDir("src@$platform")
}

fun KotlinSourceSet.testPlatformerLayout(platform: String) {
    kotlin.srcDir("test@$platform")
    resources.srcDir("testResources@$platform")
}
