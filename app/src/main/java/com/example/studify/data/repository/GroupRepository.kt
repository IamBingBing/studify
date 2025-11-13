package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.GroupModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepository @Inject constructor(private val studifyService: StudifyService){
    fun requestgroup(groupid:Int): Single<GroupModel>{
        var params = HashMap<String, Int>().apply {
            this["GROUPID"] = groupid
        }
        return studifyService.requestgroup(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}