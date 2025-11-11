package com.example.studify.Tool

import android.content.Context
import android.content.SharedPreferences
import com.example.studify.App

object TextRanges{
    const val usernameRange = 10
    const val loginIdRange = 20
    const val passwordRange = 64

    const val articleNameRange = 20
    const val articleRange = 10

    const val chatRange = 255
    const val chatNameRange = 10

    const val groupNameRange = 10

    const val hashTagRange = 10

    const val emailRange = 255
    const val addressRange = 255
    const val purpose = 20
}
object MatchingCase {
    const val fast = "fast"
    const val group = "group"
    const val exchange = "exchange"
}
private fun getSharedPreferences (): SharedPreferences{
    return App.getContext()!!.getSharedPreferences("studify", Context.MODE_PRIVATE);
}
