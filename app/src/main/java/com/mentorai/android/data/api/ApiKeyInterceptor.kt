package com.mentorai.android.data.api

import com.mentorai.android.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "x-goog-api-key",
                BuildConfig.GEMINI_AI_DEBUG_API_KEY
            )
            .build()
        return chain.proceed(request)
    }
}
