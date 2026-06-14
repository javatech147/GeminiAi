package com.mentorai.android.domain

import com.mentorai.android.data.GeminiAiRemoteDataSource
import com.mentorai.android.data.model.GeminiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeminiAiRepository @Inject constructor(
    private val remoteDataSource: GeminiAiRemoteDataSource
) {

    suspend fun generateContent(query: String): GeminiResponse {
        return withContext(Dispatchers.IO) {
            remoteDataSource.generateContent(query)
        }
    }
}
