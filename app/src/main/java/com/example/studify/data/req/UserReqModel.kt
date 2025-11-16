package com.example.studify.data.req

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class UserReqModel (
    @SerializedName("USERID") @Expose var userid: Int? = null,
    @SerializedName("USERNAME") @Expose var username: String? = null,
    @SerializedName("SEX") @Expose var sex: Int? = null, //0 : 남자 1: 여자
    @SerializedName("GROUP") @Expose var group: JSONArray? = null,
    @SerializedName("POINT") @Expose var point: Int? = null,
    @SerializedName("TENDENCY") @Expose var tendency: Int? = null,
    @SerializedName("REPORT") @Expose var report:Int?= null,
    @SerializedName("ADDRESS") @Expose var address:String?= null,
    @SerializedName("EMAIL") @Expose var email:String?=null,
    @SerializedName("CHATLIST") @Expose var chatlist: JSONArray?=null,
    @SerializedName("ID") @Expose var id: String? = null,
    @SerializedName("PASSWORD") @Expose var token:String?=null
)
