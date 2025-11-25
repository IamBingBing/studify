package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.AnnounceModel
import com.example.studify.data.model.UpdateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class noticeRepository @Inject constructor(private val studifyService: StudifyService
) {
    fun requestNoticeData(groupId: String): Single<AnnounceModel> {
        val param = HashMap<String, String>().apply {
            this["GROUPID"] = groupId.toString()
        }

        return studifyService.requestNoticeData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addNotice(
        groupId: String,
        title: String,
        content: String,
        isPin: Boolean
    ): Single<UpdateModel> {

        val param = HashMap<String, String>().apply {
            this["GROUPID"]          = groupId
            this["ANNOUNCE_NAME"]    = title
            this["ANNOUNCE_CONTENT"] = content
            this["IS_PIN"]           = if (isPin) "1" else "0"
        }

        return studifyService.addNotice(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestNoticeDetail(announceId: Long): Single<AnnounceModel> {
        val param = HashMap<String, String>().apply {
            this["ANNOUNCE_ID"] = announceId.toString()
        }

        return studifyService.requestNoticeDetail(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
