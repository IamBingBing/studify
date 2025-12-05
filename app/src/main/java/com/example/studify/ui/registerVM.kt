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
import android.util.Patterns
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

    var authcode = mutableStateOf("")

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

        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
            _registerSuccess.value = false
            _registerError.value = "올바른 이메일 형식이 아닙니다."
            return
        }

        else{requestRegister()}

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
    // 이메일 인증번호 요청 함수
    fun requestEmailCode(email: String) {
        // 여기에 실제 서버 통신 코드를 넣거나,
        // 일단 테스트를 위해 로그만 찍어둡니다.
        println("이메일 전송 요청: $email")
    }

    // 인증번호 확인 함수
    fun verifyEmailCode(email: String, code: String) {
        // 여기에 인증번호 확인 로직 구현
        println("인증번호 확인 요청: $email, $code")

        // (테스트용) 확인 버튼 누르면 성공했다고 가정
        // _registerSuccess.value = true
    }
}
