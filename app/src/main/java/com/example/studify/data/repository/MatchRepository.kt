package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.UpdateModel
import com.example.studify.data.model.isMatchModel
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
    fun requestGroupMatch(purpose: String, tendency : String) : Single<UpdateModel>{
        val params = HashMap<String,String>().apply {
            this["PURPOSE"] = purpose;
            this["TENDENCY"] = tendency
        }
        return studifyService.requestGroupMatch(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun requestMentorMatch(wantlearn: String, wantteach:String) : Single<UpdateModel>{
        val params = HashMap<String,String>().apply {
            this["WANTLEARN"]= wantlearn
            this["WANTTEACH"]=wantteach
        }
        return studifyService.requestMentorMatch(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun ismatching (): Single<isMatchModel>{
        val params = HashMap<String,String>()
        return studifyService.requestismatch(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}