package com.kashif.composemptweaks.domain

import com.kashif.composemptweaks.data.remote.RemoteDataSource
import com.kashif.composemptweaks.data.repository.DataRepository
import com.kashif.composemptweaks.platformModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module


fun initKoin(
    platform: String
) = startKoin {
    modules(platformModule(), module {
        single { createJson() }
        single { createHttpClient(get(), true) }
        single { RemoteDataSource(get(), get(), platform = platform) }
        single { DataRepository(get()) }
        single { ChatViewModel(get()) }
    })
}

fun createHttpClient(
    json: Json, enableNetworkLogs: Boolean
) = HttpClient(CIO) {

    install(ContentNegotiation) {
        json(json)
    }
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(json)
    }

    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 100000
        connectTimeoutMillis = 100000
        socketTimeoutMillis = 100000
    }

}

fun createJson() = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    prettyPrint = true
    coerceInputValues = true
}