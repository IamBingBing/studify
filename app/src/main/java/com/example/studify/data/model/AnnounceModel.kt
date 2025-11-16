package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class AnnounceModel(
    @SerializedName ("result_code") @Expose var result_code : String?= "",
    @SerializedName("error_code") @Expose var error_code : String ?= "",
    @SerializedName("result") @Expose var result : List<AnnounceContent> ?=null
) {
    data class AnnounceContent(
        @SerializedName("ANNOUNCE_ID") @Expose var announceid : Int? = -1,
        @SerializedName("ANNOUNCE_NAME") @Expose var announcename: String? = "",
        @SerializedName("ANNOUNCE_CONTENT") @Expose var announcecontent: String? = "",
        @SerializedName("ANNOUNCE_DATE") @Expose var announcedate: Timestamp? = null,
        @SerializedName("IS_PIN") @Expose var ispin: Boolean? = null,
    )
}