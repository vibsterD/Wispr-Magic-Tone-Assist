package com.example.wisprmagic.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToneAssistResponse(
    @SerialName("new_tone_message")
    val newText: String
)
