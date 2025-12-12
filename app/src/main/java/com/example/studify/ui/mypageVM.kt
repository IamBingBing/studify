package com.example.studify.ui

import android.app.Application
import android.util.Log
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
    var accountStatus = mutableStateOf("정상")

    private val disposables = CompositeDisposable()

    init {
        loadUserInfoFromPreferences()
        refreshMyInfoFromServer()
    }

    private fun buildAccountStatusText(report: Int): String {
        // 너무 강한 단정 표현은 피하고 "요약"으로만
        return when {
            report <= 0 -> "정상"
            report in 1..2 -> "주의 (누적 ${report}회)"
            report in 3..4 -> "경고 (누적 ${report}회)"
            else -> "제한 가능성 있음 (누적 ${report}회)"
        }
    }

    private fun loadUserInfoFromPreferences() {
        name.value = Preferences.getString("USERNAME") ?: ""
        email.value = Preferences.getString("EMAIL") ?: ""
        address.value = Preferences.getString("ADDRESS") ?: ""

        val sexInt = Preferences.getInt("SEX")
        sex.value = if (sexInt == 0) "남자" else "여자"

        val pointInt = Preferences.getInt("POINT")
        point.value = "${pointInt}P"

        val reportInt = Preferences.getInt("REPORT")
        accountStatus.value = buildAccountStatusText(reportInt)

        val groupJsonStr = Preferences.getString("GROUPLIST") ?: "[]"
        val groupJson = JSONArray(groupJsonStr)
        if (groupJson.length() == 0) {
            group.value = "없음"
        } else {
            group.value = ""
            (0 until groupJson.length()).forEach { idx ->
                val type = object : TypeToken<LoginModel.Result.group>() {}.type
                val groupModel: LoginModel.Result.group =
                    Gson().fromJson(groupJson.getString(idx), type)
                group.value += groupModel.groupname + ","
            }
        }
    }

    fun refreshMyInfoFromServer() {
        Log.d("MypageVM", "refreshMyInfoFromServer() 호출")

        val d = userRepository.requestUserData()
            .subscribe({ model ->
                Log.d(
                    "MypageVM",
                    "refreshMyInfoFromServer() resultCode=${model.resultCode}, error=${model.errorMsg}"
                )

                if (model.resultCode == "200" && model.result != null) {
                    val r = model.result!!

                    r.username?.let { name.value = it }
                    r.email?.let { email.value = it }
                    r.address?.let { address.value = it }
                    r.sex?.let { sex.value = if (it == 0) "남자" else "여자" }
                    r.point?.let { point.value = "${it}P" }
                    // (서버가 REPORT를 내려줘야 함)
                    r.report?.let { reportCount ->
                        accountStatus.value = buildAccountStatusText(reportCount)
                        Preferences.putInt("REPORT", reportCount)
                    }

                } else {
                    if (model.errorMsg.isNotBlank()) {
                        Toast.makeText(application, model.errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            }, { error ->
                Log.e("MypageVM", "refreshMyInfoFromServer() exception", error)
            })

        disposables.add(d)
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
                refreshMyInfoFromServer()
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