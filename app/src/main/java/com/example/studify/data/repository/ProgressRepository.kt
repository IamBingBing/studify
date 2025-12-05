package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.ProgressModel
import com.example.studify.data.model.UpdateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgressRepository @Inject constructor(private val studifyService: StudifyService) {

    fun getProgress(groupId: String): Single<ProgressModel> {
        val param = HashMap<String, String>().apply { this["GROUPID"] = groupId }
        return studifyService.requestGetProgress(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveProgress(groupId: String, purposeJson: String): Single<UpdateModel> {
        val param = HashMap<String, String>().apply {
            this["GROUPID"] = groupId
            this["PURPOSE"] = purposeJson
        }
        return studifyService.UpdateProgress(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}