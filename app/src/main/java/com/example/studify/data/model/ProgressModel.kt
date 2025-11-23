package com.example.studify.data.model

import com.google.gson.JsonArray
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProgressModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: ProgressResult? =null
) {
    data class ProgressResult (
        @SerializedName("GROUP_ID") @Expose var groupId : Long = -1,
        @SerializedName("USER_ID") @Expose var userId : Long = -1,
        @SerializedName("PURPOSE") @Expose var purposeList : List<Purpose>? = null
        ){
        data class Purpose(
            @SerializedName("PURPOSE") @Expose var purpose : String = "",
            @SerializedName("COMPLIT") @Expose var complit : Boolean = false
        )
    }
}