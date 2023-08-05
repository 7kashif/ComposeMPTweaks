package com.kashif.composemptweaks

import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController { App() }

actual fun platformModule() = module {
    single {
        Darwin.create()
    }
}

actual fun currentPlatform() = "iOS"