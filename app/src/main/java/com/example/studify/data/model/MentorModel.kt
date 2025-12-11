package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MentorModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: MentorResult? = null
) {
    data class MentorResult (
        @SerializedName("MENTORID") @Expose var mentorid : Long? = null,
        @SerializedName("MENTOR_ID") @Expose var mentorId : Long? = null,
        @SerializedName("MENTI_ID") @Expose var mentiId : Long? = null,
        @SerializedName("QNA") @Expose var qna : String = "",
        @SerializedName("MENTOR") @Expose var mentor : String = "",
        @SerializedName("MENTI") @Expose var menti : String = "",
        @SerializedName("TEACH") @Expose var teach : String = "",
        @SerializedName("LEARN") @Expose var learn : String = ""
    )
}