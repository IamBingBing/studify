package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.DateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateRepository @Inject constructor(private val studifyService: StudifyService) {

    fun requestDate(groupId:Int) : Single<DateModel> {
        val param = HashMap<String, Any>().apply {
            this["GROUPID"] = groupId
        }

        return studifyService.requestDateData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}