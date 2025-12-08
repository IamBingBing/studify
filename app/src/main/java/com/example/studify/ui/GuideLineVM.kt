package com.example.studify.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.BookModel
import com.example.studify.data.repository.GroupRepository
import com.example.studify.data.repository.GuideLineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class GuideLineVM @Inject constructor(
    private val application: Application,
    private val repository: GuideLineRepository,
    private val groupRepository: GroupRepository, // [추가] 그룹 정보 조회용
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // [변경] 목표 문자열 대신 ID를 받음 (String -> Long 변환)
    val groupId = savedStateHandle.get<String>("groupid")?.toLongOrNull() ?: -1L

    // 화면에 띄울 목표(해시태그) - 초기값은 로딩 중
    val groupGoal = mutableStateOf("목표 로딩 중...")

    var isLoading = mutableStateOf(false)
    var bookList = mutableStateListOf<BookModel.BookInfo>()

    private val disposables = CompositeDisposable()

    init {
        if (groupId != -1L) {
            loadGroupAndFetchGuide(groupId)
        } else {
            groupGoal.value = "잘못된 그룹 ID입니다."
        }
    }

    // [핵심 로직] 1. 그룹 조회 -> 2. 해시태그 추출 -> 3. AI 요청
    fun loadGroupAndFetchGuide(id: Long) {
        isLoading.value = true

        val d = groupRepository.requestGroupData()
            .subscribe({ model ->
                if (model.resultCode == "200" && model.result != null) {
                    // 내 그룹 ID와 일치하는 것 찾기
                    var foundHashtag = ""
                    model.result!!.forEach { result ->
                        if (result.groupid == id) {
                            foundHashtag = result.hashtag ?: "목표 없음"
                        }
                    }

                    if (foundHashtag.isNotBlank()) {
                        // 해시태그를 목표로 설정
                        groupGoal.value = foundHashtag
                        // AI 가이드라인 요청
                        fetchBooks(foundHashtag)
                    } else {
                        isLoading.value = false
                        groupGoal.value = "그룹 정보를 찾을 수 없습니다."
                    }
                } else {
                    isLoading.value = false
                    groupGoal.value = "서버 통신 오류"
                }
            }, { error ->
                isLoading.value = false
                Log.e("GuideLineVM", "그룹 조회 실패: ${error.message}")
            })

        disposables.add(d)
    }

    // AI에게 추천 요청
    fun fetchBooks(keyword: String) {
        // isLoading은 true 상태 유지
        bookList.clear()

        val d = repository.getRecommendations(keyword)
            .subscribe({ model ->
                isLoading.value = false
                if (model.resultCode == "200" && model.result != null) {
                    bookList.addAll(model.result!!)
                } else {
                    Toast.makeText(application, model.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                isLoading.value = false
                Log.e("GuideLineVM", "AI 요청 실패: ${error.message}")
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}