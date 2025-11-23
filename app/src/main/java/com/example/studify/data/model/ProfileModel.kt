package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("result_code") @Expose var resultCode: String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: ProfileResult? = null
) {
    data class ProfileResult(
        @SerializedName("USERID") @Expose var userId: Int = 0,
        @SerializedName("USERNAME") @Expose var username: String = "",
        @SerializedName("SEX") @Expose var sex: Int = 0,
        @SerializedName("ADDRESS") @Expose var address: String = "",
        @SerializedName("EMAIL") @Expose var email: String = "",
        @SerializedName("POINT") @Expose var point: Int = 0
    )
}