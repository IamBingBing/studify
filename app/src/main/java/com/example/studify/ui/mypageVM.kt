package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.studify.Tool.Preferences
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class mypageVM @Inject constructor(application: Application,studifyService: StudifyService): ViewModel() {
    var isEditing = mutableStateOf(false)
    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var group = mutableStateOf("")
    var sex = mutableStateOf("")
    var address = mutableStateOf("")
    var tendency = mutableStateOf("")
    var point = mutableStateOf("")

    init {
        loadUserInfoFromPreferences()
    }
    private fun loadUserInfoFromPreferences() {
        name.value = Preferences.getString("USERNAME") ?: ""
        email.value = Preferences.getString("EMAIL") ?: ""
        group.value = Preferences.getString("GROUP") ?: ""
        address.value = Preferences.getString("ADDRESS") ?: ""

        val sexInt = Preferences.getInt("SEX")
        sex.value = when (sexInt) {
            0 -> "남자"
            1 -> "여자"
            else -> "기타"
        }

        val tendencyFloat = Preferences.getFloat("TENDENCY")
        tendency.value =
            if (tendencyFloat == 0f) "" else "${tendencyFloat}"

        val pointInt = Preferences.getInt("POINT")
        point.value =
            if (pointInt == 0) "0P" else "${pointInt}P"
    }
}

