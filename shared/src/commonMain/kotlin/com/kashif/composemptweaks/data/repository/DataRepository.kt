package com.kashif.composemptweaks.data.repository

import com.kashif.composemptweaks.data.dto.MessageDTO
import com.kashif.composemptweaks.data.remote.RemoteDataSource

class DataRepository(
    private val dataSource: RemoteDataSource
) {

    suspend fun chatWebSocket(messageDTO: MessageDTO) = dataSource.chatWebSocket()

}