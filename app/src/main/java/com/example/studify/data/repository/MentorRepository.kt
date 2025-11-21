package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.MentorModel
import com.example.studify.data.model.QnaModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MentorRepository @Inject constructor(private val studifyService: StudifyService) {

    fun requestMentorData(): Single<MentorModel> {
        val param = HashMap<String, String>()

        return studifyService.requestMentorData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun requestGroupMentorQnaData(groupId: Int): Single<QnaModel> {
        val param = HashMap<String, String>().apply {
            this["GROUPID"] = groupId.toString()
        }
        return studifyService.requestgroupMentorQnaData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}