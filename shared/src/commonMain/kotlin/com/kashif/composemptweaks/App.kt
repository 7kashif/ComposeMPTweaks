package com.kashif.composemptweaks

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.core.module.Module

@Composable
fun App() {
    MaterialTheme {
        Chat()
    }
}

expect fun platformModule(): Module