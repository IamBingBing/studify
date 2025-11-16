package com.example.studify.data.req

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class GroupReqModel (
    @SerializedName("GROUPNAME") @Expose var groupName: String? = null,
    @SerializedName("MAX_LENGTH") @Expose var maxMember: Int? = null,
    @SerializedName("USERS") @Expose var users: JSONArray? = null,
    @SerializedName("ANNOUNCE") @Expose var noticeList: JSONArray? = null,
    @SerializedName("QNA") @Expose var qna: JSONArray? = null,
    @SerializedName("DATES") @Expose var dates: JSONArray? = null,
    @SerializedName("HASHTAG") @Expose var hashTags: JSONArray? = null,
    @SerializedName("HOST") @Expose var host: Int? = null,
    @SerializedName("TENDENCY") @Expose var intensity: Int? = null,
    @SerializedName("PURPOSE") @Expose var purpose: String? = null,

    )
