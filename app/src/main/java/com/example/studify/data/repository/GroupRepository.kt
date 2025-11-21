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

    fun requestGroupData(groupid: Int) : Single<GroupModel> {
        var params = HashMap<String, String>().apply {
            this["GROUPID"] = groupid.toString()
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
    ): Single<UpdateModel> {

        val param = HashMap<String, String>().apply {
            this["GROUPNAME"] = groupName
            this["MAX_LENGTH"] = maxLength.toString()
            this["HASHTAG"] = hashtag
            this["TENDENCY"] = tendency.toString()
            this["PURPOSE"] = purpose
        }

        return studifyService.UpdateGroup(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}