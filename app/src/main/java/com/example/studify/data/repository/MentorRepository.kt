package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.MentorModel
import com.example.studify.data.req.UserReqModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MentorRepository @Inject constructor(private val studifyService: StudifyService) {

    fun requestMentorData(userId: Int): Single<MentorModel> {
        val param = HashMap<String, String>().apply {
            this["USERID"] = userId.toString()

        }
        return studifyService.requestMentorData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}