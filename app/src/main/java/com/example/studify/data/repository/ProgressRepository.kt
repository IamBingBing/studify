package com.example.studify.data.repository

import android.util.Log
import com.example.studify.data.StudifyService
import com.example.studify.data.model.ProgressModel
import com.example.studify.data.model.UpdateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressRepository @Inject constructor(
    private val studifyService: StudifyService
) {

    // 진행도 화면 (내 목표)
    fun getProgress(groupId: String): Single<ProgressModel> {
        val param = HashMap<String, String>().apply {
            this["GROUPID"] = groupId
        }
        Log.d("ProgressRepository", "getProgress() param=$param")

        return studifyService.requestGetProgress(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    // 프로필 상세 (특정 유저 목표)
    fun getUserProgress(groupId: String, userId: String): Single<ProgressModel> {
        val param = HashMap<String, String>().apply {
            this["GROUPID"] = groupId
            this["USERID"] = userId
        }
        Log.d("ProgressRepository", "getUserProgress() param=$param")

        return studifyService.requestGetProgress(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveProgress(groupId: String, purposeJson: String): Single<UpdateModel> {
        val param = HashMap<String, String>().apply {
            this["GROUPID"] = groupId
            this["PURPOSE"] = purposeJson
        }
        Log.d("ProgressRepository", "saveProgress() param=$param")

        return studifyService.UpdateProgress(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
