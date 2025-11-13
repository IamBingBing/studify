package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.LoginModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val studifyService: StudifyService) {
    fun requestLogin(loginid:String, password:String) : Single<LoginModel> {
        val param = HashMap<String, String>().apply {
            this["ID"] = loginid
            this["PASSWORD"] = password
        }
        return studifyService.requestLogin(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}