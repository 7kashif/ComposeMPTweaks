package com.kashif.composemptweaks.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class MessageDTO(
    val sender: String = "",
    val message: String = ""
)
