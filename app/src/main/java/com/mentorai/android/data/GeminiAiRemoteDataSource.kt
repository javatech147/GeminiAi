package com.mentorai.android.data

import com.mentorai.android.data.api.GeminiApi
import com.mentorai.android.data.model.Content
import com.mentorai.android.data.model.GeminiRequest
import com.mentorai.android.data.model.GeminiResponse
import com.mentorai.android.data.model.Part
import java.lang.Exception
import javax.inject.Inject

class GeminiAiRemoteDataSource @Inject constructor(
    private val geminiApi: GeminiApi
) {
    suspend fun generateContent(query: String): GeminiResponse {
        return try {
            val part = Part(text = query)
            val content = Content(
                role = "", // role must be either 'model' or 'user'
                parts = listOf(part)
            )
            val geminiRequest = GeminiRequest(
                contents = listOf(content)
            )
            geminiApi.generateContent(request = geminiRequest)
        } catch (e: Exception) {
            println("Exception occur while requesting query: $e")
            GeminiResponse(listOf())
        }
    }
}
