package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.DateModel
import com.example.studify.data.model.UpdateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateRepository @Inject constructor(private val studifyService: StudifyService) {
    fun requestDateData(groupId: Int): Single<DateModel> {
        val param = HashMap<String, String>().apply {
            this["GROUPID"] = groupId.toString()
        }

        return studifyService.requestDateData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateDate(
        groupId: Int,
        title: String,
        time: String,
        content: String,
        location: String
    ): Single<UpdateModel> {

        val param = HashMap<String, String>().apply {
            this["GROUPID"] = groupId.toString()
            this["TITLE"] = title
            this["TIME"] = time
            this["CONTENT"] = content
            this["LOCATION"] = location
        }

        return studifyService.UpdateDate(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
