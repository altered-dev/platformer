plugins {
    kotlin("multiplatform")
}

group = "me.altered"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {

    // Desktop platforms

    jvm()

    macosX64()

    macosArm64()

    // Mobile platforms

//    jvm("android") {
//        withJava()
//    }

    iosX64()

    iosArm64()

    sourceSets {
        commonMain {
            kotlin.srcDir("src")
            resources.srcDir("res")
        }

        val desktopMain by creating {
            kotlin.srcDir("desktop")
            dependsOn(commonMain.get())
        }

        jvmMain {
            kotlin.srcDir("jvm")
            dependsOn(desktopMain)
        }

        macosMain {
            kotlin.srcDir("macos")
            dependsOn(desktopMain)
        }

        val mobileMain by creating {
            kotlin.srcDir("mobile")
            dependsOn(commonMain.get())
        }

//        androidMain {
//            kotlin.srcDir("android")
//            dependsOn(mobileMain)
//        }

        iosMain {
            kotlin.srcDir("ios")
            dependsOn(mobileMain)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}
