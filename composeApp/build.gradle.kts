// KMP : Desktop + WasmJs

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

val appGroup = providers.gradleProperty("app.group").get()
val appRootName = providers.gradleProperty("app.rootName").get()

group = appGroup
version = "0.1.0"

kotlin {
    jvm("desktop")
    jvmToolchain(25)

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set(appRootName)
        browser { }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val wasmJsMain by getting
    }
}

compose.desktop {
    application {
        mainClass = "$appGroup.MainKt"
    }
}

compose.resources {
    packageOfResClass = appGroup
}
