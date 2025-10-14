package com.example.studify.data.model

import com.google.gson.JsonArray
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StfChatDataModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("list") @Expose var list: List<StfChat>? =null
) : Serializable {
    data class  StfChat(
        @SerializedName("CHATNAME") @Expose var CHATNAME: String? = null,
        @SerializedName("CHAT") @Expose var CHAT: JsonArray? = null
    ):Serializable
}