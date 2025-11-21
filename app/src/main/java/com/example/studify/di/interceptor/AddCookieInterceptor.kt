package com.example.studify.di.interceptor

import android.util.Log
import android.webkit.CookieManager
import com.example.studify.Tool.Preferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = Preferences.getString("access_token")
        val request= chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}