package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.AnnounceModel
import com.example.studify.data.model.DateModel
import com.example.studify.data.model.GroupModel
import com.example.studify.data.repository.DateRepository
import com.example.studify.data.repository.GroupRepository
import com.example.studify.data.repository.noticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class groupVM @Inject constructor(
    application: Application,
    private val groupRepository: GroupRepository,
    private val noticeRepository: noticeRepository,
    private val dateRepository: DateRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val groupId = mutableStateOf(savedStateHandle.get<String>("groupid")!!)

    val groupName = mutableStateOf("")
    val groupGoal = mutableStateOf("")
    val hashTags = mutableStateOf<String>("")
    val currentTab = mutableStateOf(0)
    val users = mutableStateOf<List<GroupModel.GroupResult.user>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)
    val dates = mutableStateOf<List<DateModel.DateResult>>(emptyList())
    val announce = mutableStateOf<List<AnnounceModel.AnnounceContent>>(emptyList())

    private val disposables = CompositeDisposable()

    init {
        loadGroup(groupId.value)
        requestNotice()
        requestDates()
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
    fun requestNotice(groupid: String = groupId.value) {
        val d = noticeRepository.requestNoticeData(groupid)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    // 그냥 앞에서 2개만 잘라서 저장
                    announce.value = model.result.take(2)
                } else {
                    announce.value = emptyList()
                    // 필요하면 에러메시지 합치기
                    // errorMessage.value = model.errorMsg
                }
            }, { e ->
                e.printStackTrace()
                announce.value = emptyList()
            })

        disposables.add(d)
    }
    fun requestDates(groupid: String = groupId.value) {
        val d = dateRepository.requestDateData(groupid)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    // TIME 기준으로 정렬 후 2개만 가져오기
                    val sorted = model.result.sortedBy { it.time }
                    dates.value = sorted.take(2)
                } else {
                    dates.value = emptyList()
                }
            }, { e ->
                e.printStackTrace()
                dates.value = emptyList()
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
