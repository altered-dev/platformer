plugins {
    platformer.`mp-conventions`
}

dependencies {
    commonMainApi(project(":engine"))
    jvmMainApi(project(":engine"))
    macosX64MainApi(project(":engine"))
    macosArm64MainApi(project(":engine"))
    iosX64MainApi(project(":engine"))
    iosArm64MainApi(project(":engine"))
}
