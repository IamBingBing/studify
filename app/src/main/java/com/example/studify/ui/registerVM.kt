package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class registerVM @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : ViewModel() {

    var email = mutableStateOf("")
    var sex = mutableStateOf(-1)
    var adress = mutableStateOf("")
    var username = mutableStateOf("")
    var userid = mutableStateOf("")
    var pw = mutableStateOf("")
    var repw = mutableStateOf("")
    var expanded = mutableStateOf(false)

    var refreshToken = mutableStateOf("")
    var userId = mutableStateOf("")

    fun onExpandedChange(isExpanded: Boolean) {
        expanded.value = isExpanded
    }

    fun onSexSelected(selection: Int) {
        sex.value = selection
    }

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    private val _registerError = MutableStateFlow<String?>(null)
    val registerError = _registerError.asStateFlow()


    private val compositeDisposable = CompositeDisposable()

    fun register() {
        if (pw.value != repw.value) {
            _registerSuccess.value = false
            _registerError.value = "비밀번호와 비밀번호 재입력이 서로 다릅니다."
            requestRegister()
        }
    }

    fun requestRegister(id :String = userid.value, pwd:String = pw.value, name : String = username.value, mail: String = email.value, gender :String = sex.value.toString(), add:String = adress.value)= userRepository.RegisterUser(
        id = id,
        pw = pwd,
        username = name,
        email = mail,
        sex = gender,
        address = add
    ).subscribe({ response ->

        if (response.resultCode == "200") {

            val res = response.result
            refreshToken.value = res?.refreshToken ?: ""
            userId.value       = res?.userid ?: ""

            _registerSuccess.value = true
            _registerError.value = null

        } else {
            _registerSuccess.value = false
            _registerError.value = response.errorMsg
        }

    }, { error ->
        _registerSuccess.value = false
        _registerError.value = error.message ?: error.toString()
    })
}
