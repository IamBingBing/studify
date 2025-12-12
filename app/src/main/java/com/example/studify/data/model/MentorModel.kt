package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MentorModel(
    @SerializedName("result_code") @Expose var resultCode: String = "",
    @SerializedName("error_msg")   @Expose var errorMsg: String = "",
    @SerializedName("result")      @Expose var result: MentorResult? = null
) {
    data class MentorResult(
        @SerializedName("MENTORID")   @Expose var mentorid: Long? = null,   // 방ID
        @SerializedName("MENTOR_ID")  @Expose var mentorId: Long? = null,   // 멘토 유저ID
        @SerializedName("MENTI_ID")   @Expose var mentiId: Long? = null,    // 멘티 유저ID
        @SerializedName("MY_ID")      @Expose var myId: Long? = null,       // 내 유저ID
        @SerializedName("IS_MENTOR")  @Expose var isMentor: Int? = null,    // 1/0
        @SerializedName("QNA")        @Expose var qna: String = "",
        @SerializedName("MENTOR")     @Expose var mentor: String = "",
        @SerializedName("MENTI")      @Expose var menti: String = "",
        @SerializedName("TEACH")      @Expose var teach: String = "",
        @SerializedName("LEARN")      @Expose var learn: String = ""
    )
}