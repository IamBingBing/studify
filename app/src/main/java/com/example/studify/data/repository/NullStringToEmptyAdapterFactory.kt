package com.example.studify.data.repository

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Created by shPark on 2020/09/18.
 */
class NullStringToEmptyAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson?, type: TypeToken<T?>?): TypeAdapter<T?>? {
        return if (type?.rawType != String::class.java) {
            null
        } else StringAdapter() as TypeAdapter<T?>
    }

    internal inner class StringAdapter : TypeAdapter<String?>() {
        @Throws(IOException::class)
        override fun write(out: JsonWriter?, value: String?) {
            if (TextUtils.isEmpty(value)) {
                out!!.nullValue()
                return
            }
            out!!.value(value)
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader?): String? {
            if (`in`!!.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return ""
            }
            return `in`.nextString()
        }
    }
}