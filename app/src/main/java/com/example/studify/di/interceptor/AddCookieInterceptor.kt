package com.example.studify.di.interceptor

import android.webkit.CookieManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddCookieInterceptor : Interceptor{
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookie = CookieManager.getInstance().getCookie(chain.request().url.toString())
        if (cookie !=  null){
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }
}