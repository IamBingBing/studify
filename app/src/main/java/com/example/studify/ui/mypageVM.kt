package com.example.studify.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.Tool.Preferences
import com.example.studify.data.model.LoginModel
import com.example.studify.data.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONArray
import org.json.JSONObject
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
        address.value = Preferences.getString("ADDRESS") ?: ""

        val sexInt = Preferences.getInt("SEX")
        sex.value = if (sexInt == 0) "남자" else "여자"

        val pointInt = Preferences.getInt("POINT")
        point.value = "${pointInt}P"
        val groupJson = JSONArray(Preferences.getString("GROUPLIST"))
        if (groupJson.length() == 0){
            group.value = "없음"
        }
        else {
            (0 until groupJson.length()).forEach { key ->
                val type = object : TypeToken<LoginModel.Result.group>() {}.type
                val groupmodel: LoginModel.Result.group =
                    Gson().fromJson(groupJson.getString(key), type)
                group.value = group.value + groupmodel.groupname + ","
            }
        }
    }

    fun saveUserInfo() {
        val sexInt = if (sex.value == "남자") 0 else 1

        val d = userRepository.requestUpdateUser(
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