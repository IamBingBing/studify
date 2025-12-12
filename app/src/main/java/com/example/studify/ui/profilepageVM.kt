package com.example.studify.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.ProgressModel.ProgressResult.Purpose
import com.example.studify.data.repository.ProgressRepository
import com.example.studify.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class profilepageVM @Inject constructor(
    private val application: Application,
    private val userRepository: UserRepository,
    private val progressRepository: ProgressRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ── 기본 프로필 정보 ───────────────────────
    var name = mutableStateOf("로딩 중.")
    var email = mutableStateOf("")
    var sex = mutableStateOf("")
    var address = mutableStateOf("")
    var point = mutableStateOf("")

    // ── 개인 목표 정보 ────────────────────────
    var personalGoals = mutableStateListOf<Purpose>()
    var progressPercent = mutableStateOf(0f)

    private val targetId: Long =
        savedStateHandle.get<String>("userId")?.toLongOrNull() ?: -1L

    private val groupId: String =
        savedStateHandle.get<String>("groupId") ?: "-1"

    private val disposables = CompositeDisposable()

    private fun s(v: Any?): String = v?.toString() ?: ""

    init {
        Log.d("ProfilePageVM", "init targetId=$targetId, groupId=$groupId")

        if (targetId != -1L) {
            loadProfile(targetId)
        } else {
            Toast.makeText(application, "잘못된 사용자 접근입니다.", Toast.LENGTH_SHORT).show()
        }

        if (groupId != "-1" && targetId != -1L) {
            loadUserProgress()
        } else {
            Log.d("ProfilePageVM", "skip loadUserProgress() groupId=$groupId, targetId=$targetId")
        }
    }

    // ── 1) 프로필 정보 불러오기 ─────────────────
    fun loadProfile(id: Long) {
        Log.d("ProfilePageVM", "loadProfile() id=$id")

        val d = userRepository.requestGetProfile(id)
            .subscribe({ model ->
                Log.d(
                    "ProfilePageVM",
                    "loadProfile() resultCode=${model.resultCode}, error=${model.errorMsg}"
                )

                if (model.resultCode == "200" && model.result != null) {
                    val r = model.result!!

                    name.value = s(r.username)
                    email.value = s(r.email)
                    address.value = s(r.address)
                    val p = s(r.point)
                    point.value = if (p.isBlank()) "" else "${p}P"
                    sex.value = if (r.sex == 0) "남자" else "여자"

                    Log.d("ProfilePageVM", "profile loaded username=${name.value}, email=${email.value}")
                } else {
                    Toast.makeText(application, model.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Log.e("ProfilePageVM", "프로필 로딩 실패", error)
                Toast.makeText(
                    application,
                    "통신 오류: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            })
        disposables.add(d)
    }

    // ── 2) 개인 목표 / 달성률 불러오기 ───────────
    fun loadUserProgress() {
        if (groupId == "-1" || targetId == -1L) {
            Log.d("ProfilePageVM", "loadUserProgress() aborted groupId=$groupId, targetId=$targetId")
            return
        }

        Log.d("ProfilePageVM", "loadUserProgress() groupId=$groupId, targetId=$targetId")

        val d = progressRepository.getUserProgress(groupId, targetId.toString())
            .subscribe({ model ->
                Log.d(
                    "ProfilePageVM",
                    "loadUserProgress() resultCode=${model.resultCode}, error=${model.errorMsg}"
                )

                if (model.resultCode == "200" && model.result != null) {
                    val list = model.result!!.purposeList

                    if (!list.isNullOrEmpty()) {
                        // progressVM 규칙: [0] = 주요 목표, [1..] = 개인 목표
                        personalGoals.clear()
                        if (list.size > 1) {
                            personalGoals.addAll(list.subList(1, list.size))
                        }
                        calculateProgress()
                    } else {
                        personalGoals.clear()
                        progressPercent.value = 0f
                    }
                } else {
                    Log.e("ProfilePageVM", "진도 로딩 실패: ${model.errorMsg}")
                }
            }, { error ->
                Log.e("ProfilePageVM", "진도 로딩 예외: ${error.message}", error)
            })

        disposables.add(d)
    }

    // 달성률 계산 (0~100)
    fun calculateProgress() {
        if (personalGoals.isEmpty()) {
            progressPercent.value = 0f
        } else {
            val doneCount = personalGoals.count { it.complit }
            val total = personalGoals.size
            val percent = (doneCount.toFloat() / total.toFloat()) * 100f
            progressPercent.value = percent
        }
    }

    // ── 3) 신고 전송 ────────────────────────────
    fun sendReport(reason: String) {
        if (targetId == -1L) {
            Toast.makeText(application, "대상 사용자를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (reason.isBlank()) {
            Toast.makeText(application, "신고 내용을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("ProfilePageVM", "sendReport() targetId=$targetId, reason=$reason")

        val d = userRepository.reportUser(targetId.toString(), reason)
            .subscribe({ model ->
                if (model.resultCode == "200") {
                    Toast.makeText(application, "신고가 접수되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(application, model.errorMsg, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Log.e("ProfileReport", "신고 전송 실패", error)
                Toast.makeText(
                    application,
                    "신고 전송 실패: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
