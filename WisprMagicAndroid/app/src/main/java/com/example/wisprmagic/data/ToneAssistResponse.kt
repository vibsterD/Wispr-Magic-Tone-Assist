package com.example.wisprmagic.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToneAssistResponse(
    @SerialName("polite_tone")
    val politeText: String,
    @SerialName("professional_tone")
    val professionalText: String,
    @SerialName("social_tone")
    val socialText: String,
    @SerialName("emojify_tone")
    val emojifiedText: String
) {
    operator fun get(s: String): String {
        return when (s.lowercase()) {
            "polite" -> politeText
            "professional" -> professionalText
            "social" -> socialText
            "emojify" -> emojifiedText
            else -> throw IllegalArgumentException("Invalid key: " + s)
        }
    }
}
