package com.kashif.composemptweaks

import androidx.compose.runtime.Composable
import com.kashif.composemptweaks.domain.initKoin
import io.ktor.client.engine.java.Java
import org.koin.dsl.module

@Composable
fun MainView() {
    initKoin("Desktop")
    App()
}

actual fun platformModule() = module {
    single {
        Java.create()
    }
}

actual fun currentPlatform() = "Desktop"

@Composable
actual fun getScreenDimension(): Pair<Int, Int> {
    return Pair(800, 600)
}