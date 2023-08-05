plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.20"
}

kotlin {
    androidTarget()

    jvm("desktop")

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(libs.compose.util)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.json)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.json)
                implementation(libs.ktor.contentnegotiation)
                implementation(libs.ktor.web.sockets)
                implementation(libs.ktor.cio)
                implementation(libs.kotlin.serialization)
                implementation(libs.koin.core)
                implementation(libs.koin.compose.mp)
                implementation(libs.kotlinx.coroutines.core)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
                api(compose.animation)
                // Workaround as per https://youtrack.jetbrains.com/issue/KT-41821
                implementation("org.jetbrains.kotlinx:atomicfu:0.17.2")
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.android)
                implementation(libs.koin.compose)
                api(libs.androidx.activity.compose)
                api(libs.androidx.appcompat)
                api(libs.androidx.corektx)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation(libs.ktor.ios)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.java)
                implementation(compose.desktop.common)
            }
        }
    }
}

android {
    namespace = "com.kashif.composemptweaks"
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}