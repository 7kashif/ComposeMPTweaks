package com.kashif.composemptweaks

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import org.koin.dsl.module
import platform.CoreGraphics.CGRect
import platform.UIKit.UIScreen

fun MainViewController() = ComposeUIViewController { App() }

actual fun platformModule() = module {
    single {
        Darwin.create()
    }
}

actual fun currentPlatform() = "iOS"

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun getScreenDimension(): Pair<Int, Int> {
    val rect: CValue<CGRect> = UIScreen.mainScreen.bounds
    val width = rect.useContents { this.size.width }
    val height = rect.useContents { this.size.height }
    return Pair(width.toInt(), height.toInt())
}