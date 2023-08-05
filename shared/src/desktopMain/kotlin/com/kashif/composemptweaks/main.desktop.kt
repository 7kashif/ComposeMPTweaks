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