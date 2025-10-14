package com.example.studify.data.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by shPark on 2020/09/18.
 */
object GsonUtils {
    fun create(): Gson? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())
        gsonBuilder.serializeNulls()
        return gsonBuilder.create()
    }
}