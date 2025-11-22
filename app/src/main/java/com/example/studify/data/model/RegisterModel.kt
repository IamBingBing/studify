package com.example.studify.data.model

import com.example.studify.data.model.QnaModel.QnaResult
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: Result?= null
    ){
    data class Result(
        @SerializedName("REFRESH_TOKEN") @Expose var refreshToken : String = "",
        @SerializedName("USERID") @Expose var userid: String = ""
    )
}