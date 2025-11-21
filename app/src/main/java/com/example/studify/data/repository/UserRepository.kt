package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.LoginModel
import com.example.studify.data.model.UpdateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val studifyService: StudifyService) {
    fun requestLogin(loginid:String, password:String,token:String = "") : Single<LoginModel> {
        val param = HashMap<String, String>().apply {
            this["ID"] = loginid
            this["PASSWORD"] = password
            this["TOKEN"] = token
        }
        return studifyService.requestLogin(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
    fun requestUserData (): Single<LoginModel>{
        val param = HashMap<String, String>().apply {

        }
        return studifyService.requestUserData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun UpdateUser (
        id: String,
        pw: String,
        username: String,
        email: String,
        sex: String,
        address: String
    ) : Single<UpdateModel> {
        val param = HashMap<String, String>().apply {
            this["ID"] = id.toString()             // 이미 String
            this["PASSWORD"] = pw.toString()             // String
            this["USERNAME"] = username.toString()       // String
            this["EMAIL"] = email.toString()          // String
            this["SEX"] = sex.toString()            // Int → String
            this["ADDRESS"] = address.toString()         // String
        }
        return studifyService.UpdateUser(param)
    }
    fun requestUpdateUser(name: String, email: String, address: String, sex: Int): Single<UpdateModel> {
        val param = HashMap<String, String>().apply {
            this["USERNAME"] = name
            this["EMAIL"] = email
            this["ADDRESS"] = address
            this["SEX"] = sex.toString()
        }
        return studifyService.requestUpdateUser(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}