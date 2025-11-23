package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnnounceModel(
    @SerializedName("result_code") @Expose var resultCode: String = "",
    @SerializedName("error_msg")   @Expose var errorMsg: String = "",
    @SerializedName("result")      @Expose var result: List<AnnounceContent> = emptyList()
) {
    data class AnnounceContent(
        @SerializedName("ANNOUNCE_ID")      @Expose var announceId: Long? = null,
        @SerializedName("ANNOUNCE_NAME")    @Expose var announceName: String? = "",
        @SerializedName("ANNOUNCE_CONTENT") @Expose var announceContent: String? = "",
        @SerializedName("ANNOUNCE_DATE")    @Expose var announceDate: String? = null,
        @SerializedName("IS_PIN")           @Expose var isPin: Boolean? = null,
    )
}