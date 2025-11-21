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

    fun requestRegister() {
        if (pw.value != repw.value) {
            _registerSuccess.value = false
            _registerError.value = "비밀번호와 비밀번호 재입력이 서로 다릅니다."
            return
        }

        val disposable = userRepository.UpdateUser(
            id = userid.value,
            pw = pw.value,
            username = username.value,
            email = email.value,
            sex = sex.value.toString(),
            address = adress.value
        ).subscribe({ response ->
            if (response.resultCode == "200") {
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

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
