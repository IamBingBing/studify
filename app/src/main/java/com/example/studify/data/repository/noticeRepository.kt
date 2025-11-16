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
    fun requestNoticeList(groupId: Int): Single<AnnounceModel> {
        val param = HashMap<String, Any>().apply {
            this["GROUP_ID"] = groupId
        }

        return studifyService.requestAnnounce(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object
}
