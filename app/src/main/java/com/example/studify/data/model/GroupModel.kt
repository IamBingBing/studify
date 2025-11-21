package com.example.studify.data.model

import com.google.gson.JsonArray
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GroupModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: GroupResult? =null
) : Serializable {
    data class  GroupResult(
        @SerializedName("GROUPID") @Expose var groupid: Int? = null,
        @SerializedName("GROUPNAME") @Expose var groupname: String? = null,
        @SerializedName("MAX_LENGTH") @Expose var maxlength: Int? = null,
        @SerializedName("USERS") @Expose var users: List<String>? = null,
        @SerializedName("ANNOUNCE") @Expose var annouce: List<String>? = null,
        @SerializedName("QNA") @Expose var qna: List<String>? = null,
        @SerializedName("DATES") @Expose var dates: List<String>? = null,
        @SerializedName("HASHTAG") @Expose var hashtag: String?=null,
        @SerializedName("HOST") @Expose var host: Int? = null,
        @SerializedName("TENDENCY") @Expose var tendency: Int? = null,
        @SerializedName("PURPOSE") @Expose var purpose: String ? = null,
        @SerializedName ("GROUPTYPE") @Expose var grouptype : Int = 0 //0 : 번개 1: 그룹 2: 멘토
    ):Serializable
}