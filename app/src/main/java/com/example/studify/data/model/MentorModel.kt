package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MentorModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: MentorResult? =null
) {
    data class MentorResult (
        @SerializedName("MENTORID") @Expose var mentorid : Int? =null,
        @SerializedName("QNA") @Expose var qna : String = "",
        @SerializedName("MENTOR") @Expose var mentor : String = "",
        @SerializedName("MENTI") @Expose var menti : String = "",
        @SerializedName("TEACH") @Expose var teach : String = "",

        )
}