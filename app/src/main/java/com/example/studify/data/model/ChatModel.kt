package com.example.studify.data.model

import com.google.gson.JsonArray
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp

data class ChatModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var list: List<StfChat>? =null
) : Serializable {
    data class  StfChat(
        @SerializedName("ID") @Expose var ID: Long? = null,
        @SerializedName("GROUPID") @Expose var GROUPID: Long? = null,
        @SerializedName("CHATNAME") @Expose var CHATNAME: String? = null,
        @SerializedName("CHAT") @Expose var CHAT: String? = null,
        @SerializedName("TIME") @Expose var TIME : Timestamp? = null
    ):Serializable
}