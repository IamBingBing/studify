package com.example.studify.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.json.JSONArray
import java.io.Serializable

data class LoginModel(
        @SerializedName("result_code") @Expose var resultCode: String = "",
        @SerializedName("error_msg") @Expose var errorMsg: String = "",
        @SerializedName("result") @Expose var result: Result? = null,
        ) {
    data class Result(
        @SerializedName("USERID") @Expose var userid: Long? = null,
        @SerializedName("USERNAME") @Expose var username: String? = null,
        @SerializedName("ID") @Expose var id: String? = null,
        @SerializedName("SEX") @Expose var sex: Int? = null, //0 : 남자 1: 여자
        @SerializedName("GROUPLIST") @Expose var grouplist: List<group>? = null,
        @SerializedName("POINT") @Expose var point: Int? = null,
        @SerializedName("TENDENCY") @Expose var tendency: Float = 0f,
        @SerializedName("REPORT") @Expose var report:Int?= null,
        @SerializedName("ADDRESS") @Expose var address:String?= null,
        @SerializedName("EMAIL") @Expose var email:String?=null,
        @SerializedName("CHATLIST") @Expose var chatlist: List<chat>?=null,
        @SerializedName("TOKEN") @Expose var token:String?=null
        ){
        data class chat (
            @SerializedName("CHATID") @Expose var chatid: Long? = null,
            @SerializedName("ROOMNAME") @Expose var roomname: String = ""
        ): Serializable
        data class group (
            @SerializedName("GROUPID") @Expose var groupid: Long? = null,
            @SerializedName("GROUPNAME") @Expose var groupname: String = "",
        ):Serializable
    }
}