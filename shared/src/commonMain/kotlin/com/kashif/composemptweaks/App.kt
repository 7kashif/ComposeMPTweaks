package com.kashif.composemptweaks

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.core.module.Module

@Composable
fun App() {
    MaterialTheme {
        FluidBottomBar()
    }
}

expect fun platformModule(): Module

expect fun currentPlatform(): String

@Composable
expect fun getScreenDimension(): Pair<Int, Int>
