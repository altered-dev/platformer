plugins {
    platformer.`app-conventions`
}

kotlin {
    jvm()

    sourceSets {
        jvmMain {
            kotlin.srcDir("src")
            resources.srcDir("res")
            dependencies {
                implementation(project(":common"))
            }
        }
    }
}
