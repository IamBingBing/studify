package com.example.studify.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class profilepageVM @Inject constructor(
    private val application: Application,
    private val repository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var name = mutableStateOf("로딩 중...")
    var email = mutableStateOf("")
    var sex = mutableStateOf("")
    var address = mutableStateOf("")
    var point = mutableStateOf("")
    private val targetId: Int = savedStateHandle.get<String>("userId")?.toIntOrNull() ?: -1
    private val disposables = CompositeDisposable()

    init {
        if (targetId != -1) {
            loadProfile(targetId)
        } else {
            Toast.makeText(application, "잘못된 사용자 접근입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun loadProfile(id: Int) {
        val d = repository.requestGetProfile(id)
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    val r = model.result!!
                    name.value = r.username
                    email.value = r.email
                    address.value = r.address
                    point.value = "${r.point}P"
                    sex.value = if (r.sex == 0) "남자" else "여자"
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