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

    // ===== State Variables =====
    var email = mutableStateOf("")
    var authcode = mutableStateOf("")
    var sex = mutableStateOf(0)
    var adress = mutableStateOf("")
    var username = mutableStateOf("")
    var userid = mutableStateOf("")
    var pw = mutableStateOf("")
    var repw = mutableStateOf("")

    // UI Logic States
    var isCodeSent = mutableStateOf(false)      // 인증번호 발송 여부 (true면 이메일 수정 불가)
    var isEmailVerified = mutableStateOf(false) // 인증 완료 여부

    // Results
    var refreshToken = mutableStateOf("")
    var userId = mutableStateOf("")

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    private val _registerError = MutableStateFlow<String?>(null)
    val registerError = _registerError.asStateFlow()

    // RxJava Disposable Container
    private val compositeDisposable = CompositeDisposable()

    fun onSexSelected(selection: Int) { sex.value = selection }

    // =========================================================================
    // [1] 이메일 인증 요청 (중복 체크 포함)
    // =========================================================================
    fun requestEmailCode(emailInput: String = email.value) {
        // 1. 앱 내부 유효성 검사 (서버 부하 줄이기)
        if (emailInput.isBlank()) {
            _registerError.value = "이메일을 입력해주세요."
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            _registerError.value = "올바른 이메일 형식이 아닙니다."
            return
        }
        if (!emailInput.endsWith("@hknu.ac.kr")) {
            _registerError.value = "한경대 이메일(@hknu.ac.kr)만 사용 가능합니다."
            return
        }

        // 2. 서버 요청
        val d = userRepository.requestEmailCode(emailInput)
            .subscribe({ response ->
                // PHP 응답 처리
                if (response.resultCode == "200") {
                    // [성공] 중복 아님 + 메일 발송됨
                    isCodeSent.value = true         // UI: 이메일 입력창 잠금 & 인증번호 창 오픈
                    _registerError.value = null     // 에러 메시지 초기화
                } else {
                    // [실패] 이미 가입된 이메일 (PHP: resultCode=400, msg="이미 가입된...")
                    isCodeSent.value = false        // UI: 이메일 수정 가능 상태 유지
                    _registerError.value = response.errorMsg ?: "인증번호 발송 실패"
                }
            }, { error ->
                // 네트워크 오류 등
                isCodeSent.value = false
                _registerError.value = "통신 오류: ${error.message}"
            })

        compositeDisposable.add(d) // 메모리 관리 등록
    }

    // =========================================================================
    // [2] 인증번호 확인
    // =========================================================================
    fun verifyEmailCode(emailInput: String = email.value, codeInput: String = authcode.value) {
        if (codeInput.isBlank()) {
            _registerError.value = "인증번호를 입력해주세요."
            return
        }

        val d = userRepository.AuthEmailCode(emailInput, codeInput)
            .subscribe({ response ->
                if (response.resultCode == "200" && response.result == "true") {
                    isEmailVerified.value = true
                    _registerError.value = null // 성공 시 에러 메시지 제거 (또는 "인증되었습니다" 표시)
                } else {
                    isEmailVerified.value = false
                    _registerError.value = response.errorMsg ?: "인증번호가 일치하지 않습니다."
                }
            }, { error ->
                isEmailVerified.value = false
                _registerError.value = "통신 오류: ${error.message}"
            })

        compositeDisposable.add(d)
    }

    // =========================================================================
    // [3] 최종 회원가입 요청
    // =========================================================================
    fun register() {
        // 최종 유효성 검사
        if (userid.value.isBlank() || pw.value.isBlank() || username.value.isBlank()) {
            _registerError.value = "모든 필수 정보를 입력해주세요."
            return
        }
        if (pw.value != repw.value) {
            _registerSuccess.value = false
            _registerError.value = "비밀번호가 일치하지 않습니다."
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

                // 코루틴 스코프에서 flow emit
                viewModelScope.launch {
                    _registerSuccess.emit(true)
                }
                _registerError.value = null
            } else {
                viewModelScope.launch { _registerSuccess.emit(false) }
                _registerError.value = response.errorMsg ?: "회원가입 실패"
            }
        }, { error ->
            _registerError.value = "서버 연결 실패: ${error.message}"
        })

        compositeDisposable.add(d)
    }

    // ViewModel이 사라질 때 Disposable 정리 (메모리 누수 방지)
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}