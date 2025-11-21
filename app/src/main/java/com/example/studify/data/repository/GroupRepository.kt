package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.GroupModel
import com.example.studify.data.model.QnaModel
import com.example.studify.data.model.UpdateModel
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
    fun requestgroupQnaData(groupid: Int) : Single<QnaModel>{
        var params = HashMap<String, Any>().apply {
            this["GROUPID"] = groupid
        }
        return studifyService.requestgroupQnaData(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun requestGroupData(groupid: Int) : Single<GroupModel> {
        var params = HashMap<String, Any>().apply {
            this["GROUPID"] = groupid
        }
        return studifyService.requestGroupData(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createGroup(
        groupName: String,
        maxLength: Int,
        hashtag: String,
        tendency: Int,
        purpose: String,
        hostUserId: Int,     // 방장 USERID
    ): Single<UpdateModel> {

        val param = HashMap<String, Any>().apply {
            this["GROUPNAME"] = groupName
            this["MAX_LENGTH"] = maxLength
            this["HASHTAG"] = hashtag
            this["TENDENCY"] = tendency
            this["PURPOSE"] = purpose
            this["HOST"] = hostUserId
        }

        return studifyService.UpdateGroup(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}