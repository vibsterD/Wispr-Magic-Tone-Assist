package com.example.wisprmagic.data

import kotlinx.serialization.Serializable

@Serializable
data class ToneAssistRequest(
    val text: String
)
