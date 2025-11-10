package com.example.studify.data

import com.example.studify.data.model.LoginModel
import com.example.studify.data.model.StfChatDataModel
import io.reactivex.Single
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StudifyService {
    @FormUrlEncoded
    @POST("api/login.php")
    fun requestLogin(@FieldMap data: Map<String, String>): Single<LoginModel>
    @FormUrlEncoded
    @POST("api/chat.php")
    fun requestchat(@FieldMap data: Map<String, String>): Single<StfChatDataModel>
}