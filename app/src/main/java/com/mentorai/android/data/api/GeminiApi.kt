package com.mentorai.android.data.api

import com.mentorai.android.data.model.GeminiRequest
import com.mentorai.android.data.model.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Google supports API key authentication in two ways:
 * Option1: Query Parameter:
 *     POST https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=API_KEY
 * Option2: Header
 *     POST https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
 *     Pass header with key name: x-goog-api-key
 * Here I am using Option2 i.e. passing api key to header,
 * but I am using Global interceptor [ApiKeyInterceptor],
 * so that no need to pass @Header to each request type.
 */

interface GeminiApi {
    @POST(
        "v1beta/models/gemini-2.5-flash:generateContent"
    )
    suspend fun generateContent(
        @Body request: GeminiRequest
    ): GeminiResponse
}
