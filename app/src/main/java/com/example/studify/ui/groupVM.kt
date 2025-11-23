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

    val groupId = mutableStateOf(savedStateHandle.get<String>("groupid")!!)

    val groupName = mutableStateOf("")
    val groupGoal = mutableStateOf("")
    val hashTags = mutableStateOf<String>("")
    val currentTab = mutableStateOf(0)
    val users = mutableStateOf<List<GroupModel.GroupResult.user>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)
    private val disposables = CompositeDisposable()

    init {
        loadGroup(groupId.value)
    }

    fun loadGroup(id: String) {

        errorMessage.value = null

        val d = groupRepository.requestGroupData()
            .subscribe({ model ->

                if (model.resultCode == "200") {
                    model.result!!.forEach(
                        {
                            result-> if ( result.groupid.toString() == id){
                                groupName.value = result.groupname !!
                                groupGoal.value = result.purpose !!

                                hashTags.value = result.hashtag!!

                                users.value = result.users ?: emptyList()
                            }
                        }
                    )



                } else {
                    errorMessage.value = model.errorMsg.ifBlank { "그룹 정보를 불러오지 못했습니다." }
                    users.value = emptyList()
                }
            }, { /*e ->
                errorMessage.value = e.message
                users.value = emptyList()
                e.printStackTrace()
            */})

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
