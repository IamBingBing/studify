package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.AnnounceModel
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
}
