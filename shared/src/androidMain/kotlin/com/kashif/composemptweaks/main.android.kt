package com.kashif.composemptweaks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import com.kashif.composemptweaks.domain.initKoin
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

@Composable
fun MainView(){
    initKoin("Android")
    App()
}

actual fun platformModule() = module {
    single {
        Android.create()
    }
}

actual fun currentPlatform() = "Android"

@Composable
actual fun getScreenDimension(): Pair<Int, Int> {
    val configuration = LocalConfiguration.current
    return Pair(configuration.screenWidthDp, configuration.screenHeightDp)
}