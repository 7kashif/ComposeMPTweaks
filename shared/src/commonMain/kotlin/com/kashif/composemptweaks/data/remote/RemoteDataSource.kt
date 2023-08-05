package com.kashif.composemptweaks.data.remote

import com.kashif.composemptweaks.data.dto.MessageDTO
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class RemoteDataSource(
    private val httpClient: HttpClient,
    private val json: Json,
    private val platform: String
) {

    init {
        chatWebSocket()
    }

    fun chatWebSocket() = flow {
        //replace LOCAL_HOST with your IP Address if websocket is running on your local device.
        val url = "ws://LOCAL_HOST:8080/chat/$platform"

        httpClient.webSocket(
            urlString = url,
        ) {
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val messageJson = frame.readText()
//                    val item = json.decodeFromString<MessageDTO>(messageJson)
                    println("Received message from server: $messageJson")
                    emit(MessageDTO())
                }
            }
        }
    }

}