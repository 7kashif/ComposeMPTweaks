package com.kashif.composemptweaks.data.remote

import com.kashif.composemptweaks.data.dto.MessageDTO
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class RemoteDataSource(
    private val httpClient: HttpClient,
    private val json: Json,
    private val platform: String
) {
    private lateinit var session: DefaultClientWebSocketSession

    fun chatWebSocket() = flow {
        val url = "ws://YOUR_IP_ADDRESS_HERE:8080/chat/$platform"

        httpClient.webSocket(
            urlString = url,
        ) {
            session = this
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val messageJson = frame.readText()
                    val item = json.decodeFromString<MessageDTO>(messageJson)
                    emit(item)
                }
            }
        }
    }

    suspend fun sendMessage(messageDTO: MessageDTO) {
        session.send(json.encodeToString(MessageDTO.serializer(), messageDTO))
    }

}