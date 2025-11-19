package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class DateModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: List<DateResult> = emptyList()
) {
    data class DateResult (
        @SerializedName("DATEID") @Expose var dateid : Int? =null,
        @SerializedName("LOCATION") @Expose var location : String = "",
        @SerializedName("TIME") @Expose var time : String = "",
        @SerializedName("TITLE") @Expose var title : String = "",
        @SerializedName("CONTENT") @Expose var content : String = "",

        )
}