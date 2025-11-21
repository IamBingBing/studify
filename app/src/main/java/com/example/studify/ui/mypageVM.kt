package com.example.studify.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.Tool.Preferences
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class mypageVM @Inject constructor(
    private val application: Application,
    private val userRepository: UserRepository
) : ViewModel() {

    var isEditing = mutableStateOf(false)
    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var group = mutableStateOf("")
    var sex = mutableStateOf("")
    var address = mutableStateOf("")
    var point = mutableStateOf("")

    private val disposables = CompositeDisposable()

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

        val pointInt = Preferences.getInt("POINT")
        point.value = "${pointInt}P"

    }

    fun saveUserInfo() {
        val sexInt = if (sex.value == "남자") 0 else 1

        val d = userRepository.requestUpdatiUser(
            name = name.value,
            email = email.value,
            address = address.value,
            sex = sexInt
        ).subscribe({ model ->
            if (model.resultCode == "200") {
                Preferences.putString("USERNAME", name.value)
                Preferences.putString("EMAIL", email.value)
                Preferences.putString("ADDRESS", address.value)
                Preferences.putInt("SEX", sexInt)

                isEditing.value = false
                Toast.makeText(application, "정보가 수정되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(application, model.errorMsg, Toast.LENGTH_SHORT).show()
            }
        }, { error ->
            Toast.makeText(application, "통신 오류: ${error.message}", Toast.LENGTH_SHORT).show()
        })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}