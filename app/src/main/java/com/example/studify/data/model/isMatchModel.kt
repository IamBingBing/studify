package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class isMatchModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result : Boolean): Serializable