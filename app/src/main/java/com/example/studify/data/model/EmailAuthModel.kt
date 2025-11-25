package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmailAuthModel(
    @SerializedName("result_code") @Expose var resultCode: String = "",
    @SerializedName("error_msg")   @Expose var errorMsg: String = "",
    @SerializedName("result")      @Expose var result: Result? = null
) {
    data class Result(
        @SerializedName("AUTH_CODE") @Expose var authCode: String = ""
    )
}