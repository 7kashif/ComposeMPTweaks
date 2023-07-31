package com.kashif.composemptweaks

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform