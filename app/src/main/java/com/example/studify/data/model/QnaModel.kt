package com.example.studify.data.model

import com.example.studify.data.model.GroupModel.GroupResult
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QnaModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: List<QnaResult>? = emptyList()
) {
    data class QnaResult (
        @SerializedName("QNA_ID") @Expose var qnaid : Int? =null,
        @SerializedName("QNA_TITLE") @Expose var qnatitle : String = "",
        @SerializedName("QNA_CONTENT") @Expose var qnacontent : String = "",
        @SerializedName("QNA_ANSWER") @Expose var qnaanswer : String = "",
        )
}