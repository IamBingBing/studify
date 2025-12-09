package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.MentorModel
import com.example.studify.data.model.QnaModel
import com.example.studify.data.model.UpdateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MentorRepository @Inject constructor(private val studifyService: StudifyService) {

    fun requestMentorData(mentorId: Long): Single<MentorModel> {
        val param = HashMap<String, String>().apply {
            this["MENTORID"] = mentorId.toString()
        }

        return studifyService.requestMentorData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestMentorQnaData(mentorId: Long): Single<QnaModel> {
        val param = HashMap<String, String>().apply {
            this["MENTORID"] = mentorId.toString()
        }
        return studifyService.requestMentorQnaData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestAddMentorQna(mentorId: Long, title: String, content: String): Single<UpdateModel> {
        val param = HashMap<String, String>().apply {
            this["MENTORID"] = mentorId.toString()
            this["QNA_TITLE"] = title
            this["QNA_CONTENT"] = content
        }
        return studifyService.requestAddMentorQna(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestAddAnswer(qnaId: Long, content: String): Single<UpdateModel> {
        val param = HashMap<String, String>().apply {
            this["QNA_ID"] = qnaId.toString()
            this["ANSWER"] = content
        }
        return studifyService.requestAddAnswer(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}