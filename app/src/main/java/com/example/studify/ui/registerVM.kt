package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.studify.data.StudifyService
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class registerVM @Inject constructor(appliction: Application, private val userRepository: UserRepository, studifyService: StudifyService) : ViewModel(){
    var email = mutableStateOf("")
    var sex = mutableStateOf(-1)
    var adress = mutableStateOf("")
    var username = mutableStateOf("")
    var userid = mutableStateOf("")
    var pw = mutableStateOf("")
    var repw = mutableStateOf("")

    var expanded = mutableStateOf(false)

    // 결과 상태
    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    private val _registerError = MutableStateFlow("")
    val registerError = _registerError.asStateFlow()

    fun onExpandedChange(isExpanded: Boolean) {
        expanded.value = isExpanded
    }

    fun onSexSelected(selection: Int) {
        sex.value = selection
    }

    fun requestRegister() {
        userRepository.UpdateUser(
            id = userid.value,
            pw = pw.value,
            username = username.value,
            email = email.value,
            sex = sex.value.toString(),
            address = adress.value
        ).subscribe({ response ->

            if (response.resultCode == "200") {
                _registerSuccess.value = true
            } else {
                _registerError.value = response.errorMsg
            }

        }, { error ->
            _registerError.value = error.toString()
        })
    }
}
