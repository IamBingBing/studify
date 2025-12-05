package com.example.studify.ui

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class registerVM @Inject constructor(
    application: Application,
    private val userRepository: UserRepository
) : ViewModel() {

    var email = mutableStateOf("")
    var authcode = mutableStateOf("")
    var sex = mutableStateOf(0)
    var adress = mutableStateOf("")
    var username = mutableStateOf("")
    var userid = mutableStateOf("")
    var pw = mutableStateOf("")
    var repw = mutableStateOf("")
    var expanded = mutableStateOf(false)

    var refreshToken = mutableStateOf("")
    var userId = mutableStateOf("")

    var isCodeSent = mutableStateOf(false)
    var isEmailVerified = mutableStateOf(false)

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    private val _registerError = MutableStateFlow<String?>(null)
    val registerError = _registerError.asStateFlow()

    private val compositeDisposable = CompositeDisposable()

    fun onExpandedChange(isExpanded: Boolean) { expanded.value = isExpanded }
    fun onSexSelected(selection: Int) { sex.value = selection }

    fun requestEmailCode(emailInput: String) {
        if (emailInput.isBlank()) {
            _registerError.value = "이메일을 입력해주세요."
            return
        }

        val d = userRepository.requestEmailCode(emailInput)
            .subscribe({ response ->
                if (response.resultCode == "200") {
                    isCodeSent.value = true
                    _registerError.value = null
                } else {
                    _registerError.value = response.errorMsg ?: "인증번호 발송 실패"
                }
            }, { error ->
                _registerError.value = "통신 오류: ${error.message}"
            })

        compositeDisposable.add(d)
    }

    fun verifyEmailCode(emailInput: String = email.value, codeInput: String = authcode.value)=userRepository.AuthEmailCode(emailInput,codeInput)
        .subscribe({ response ->
            if (response.resultCode == "200") {
                if (response.result == "true") {
                    isEmailVerified.value = true
                    _registerError.value = "인증되었습니다."
                }
                else {
                    isEmailVerified.value = false
                    _registerError.value = response.errorMsg ?: "인증번호가 틀렸습니다."
                }
            }
        }, { error ->
            _registerError.value = "통신 오류: ${error.message}"
        })

    fun register() {
        if (userid.value.isBlank() || pw.value.isBlank() || username.value.isBlank()) {
            _registerError.value = "모든 필수 정보를 입력해주세요."
            return
        }
        if (pw.value != repw.value) {
            _registerSuccess.value = false
            _registerError.value = "비밀번호가 일치하지 않습니다."
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            _registerError.value = "올바른 이메일 형식이 아닙니다."
            return
        }

        if (!isEmailVerified.value) {
            _registerError.value = "이메일 인증을 완료해주세요."
            return
        }

        requestRegister()
    }

    private fun requestRegister() {
        val d = userRepository.RegisterUser(
            id = userid.value,
            pw = pw.value,
            username = username.value,
            email = email.value,
            sex = sex.value.toString(),
            address = adress.value
        ).subscribe({ response ->
            if (response.resultCode == "200") {
                val res = response.result
                refreshToken.value = res?.refreshToken ?: ""
                userId.value       = res?.userid ?: ""

                viewModelScope.launch { _registerSuccess.emit(true) }
                _registerError.value = null
            } else {
                _registerSuccess.value = false
                _registerError.value = response.errorMsg
            }
        }, { error ->
            _registerError.value = "서버 연결 실패: ${error.message}"
        })
        compositeDisposable.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}