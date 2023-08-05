package com.kashif.composemptweaks.domain

import com.kashif.composemptweaks.data.dto.MessageDTO
import com.kashif.composemptweaks.data.repository.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: DataRepository
) {
    private val _messages = MutableStateFlow<List<MessageDTO>>(emptyList())
    val messages = _messages.asStateFlow()


    fun initiateChat() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                repository.chatWebSocket(MessageDTO()).collectLatest { message ->
                    _messages.update {
                        it + message
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}