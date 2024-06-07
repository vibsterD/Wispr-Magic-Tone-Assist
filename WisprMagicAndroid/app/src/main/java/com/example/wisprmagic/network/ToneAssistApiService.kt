package com.example.wisprmagic.network

import com.example.wisprmagic.data.ToneAssistRequest
import com.example.wisprmagic.data.ToneAssistResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

//curl -X POST -H 'Content-Type: application/json' --data-binary '{"text": "I love to run!"}' https://vibsterd--groq-app-groq-it-up-dev.modal.run/


private const val BASE_URL = "https://vibsterd--groq-app-groq-it-up-dev.modal.run/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


interface ToneAssistApiService {
    @POST("/")
    suspend fun getToneAssist(
        @Body
        request: ToneAssistRequest
    ): ToneAssistResponse
}

object ToneAssistApi {
    val retrofitService: ToneAssistApiService by lazy {
        retrofit.create(ToneAssistApiService::class.java)
    }
}
