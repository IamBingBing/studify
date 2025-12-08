package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QnaModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: List<QnaResult>? = emptyList()
) {
    data class QnaResult (
        // ✅ String으로 받아서 안전하게 Long으로 변환
        @SerializedName("QNA_ID") @Expose var qnaid : String? = null,
        @SerializedName("QNA_TITLE") @Expose var qnatitle : String = "",
        @SerializedName("QNA_CONTENT") @Expose var qnacontent : String = "",
        @SerializedName("QNA_ANSWER") @Expose var qnaanswer : String = "",
    )
}