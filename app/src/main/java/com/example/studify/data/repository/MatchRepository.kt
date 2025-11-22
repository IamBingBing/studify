package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.UpdateModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MatchRepository @Inject constructor(private val studifyService: StudifyService) {
    fun requestFastMatch(start :Int, end :Int) : Single<UpdateModel>{
        val params = HashMap<String,String>().apply {
            this["STARTTIME"] = start.toString()
            this["ENDTIME"] = end.toString()
        }
        return studifyService.requestFastMatch(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun requestGroupMatch() : Single<UpdateModel>{
        val params = HashMap<String,String>().apply {

        }
        return studifyService.requestGroupMatch(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun requestMentorMatch() : Single<UpdateModel>{
        val params = HashMap<String,String>().apply {

        }
        return studifyService.requestMentorMatch(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}