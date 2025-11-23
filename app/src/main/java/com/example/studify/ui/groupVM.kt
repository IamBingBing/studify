package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.GroupModel
import com.example.studify.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class groupVM @Inject constructor(
    application: Application,
    private val groupRepository: GroupRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupId = mutableStateOf(savedStateHandle["groupid"] ?: 0)

    val groupName = mutableStateOf("")
    val groupGoal = mutableStateOf("")
    val hashTags = mutableStateOf<List<String>>(emptyList())
    val currentTab = mutableStateOf(0)
    val users = mutableStateOf<List<GroupModel.GroupResult.user>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)
    private val disposables = CompositeDisposable()

    init {
        loadGroup(groupId.value)
    }

    fun loadGroup(id: Int? = groupId.value) {
        val realId = id ?: return

        errorMessage.value = null

        val d = groupRepository.requestGroupData(realId)
            .subscribe({ model ->

                if (model.resultCode == "200" && !model.result.isNullOrEmpty()) {
                    val result = model.result!!.first()

                    groupName.value = result.groupname ?: ""
                    groupGoal.value = result.purpose ?: ""

                    val tagStr = result.hashtag ?: ""
                    hashTags.value =
                        tagStr.split(",", " ", "#")
                            .map { it.trim() }
                            .filter { it.isNotEmpty() }

                    users.value = result.users ?: emptyList()

                } else {
                    errorMessage.value = model.errorMsg.ifBlank { "그룹 정보를 불러오지 못했습니다." }
                    users.value = emptyList()
                }
            }, { e ->
                errorMessage.value = "서버 통신 실패"
                users.value = emptyList()
                e.printStackTrace()
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
