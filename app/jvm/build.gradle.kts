plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
}

sourceSets.main {
    kotlin.srcDir("src")
    resources.srcDir("res")
}
