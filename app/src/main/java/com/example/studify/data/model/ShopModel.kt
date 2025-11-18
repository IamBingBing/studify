package com.example.studify.data.model

import com.example.studify.data.model.GroupModel.GroupResult
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ShopModel (
    @SerializedName("result_code") @Expose var resultCode : String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: List<shopResult>? =null
)  {
    data class shopResult(
        @SerializedName("GOOD_ID") @Expose var userid: Int? = null,
        @SerializedName("PRICE") @Expose var price: Int? = null,
        @SerializedName("GOOD_NAME") @Expose var good_name: String? = null,
        @SerializedName("DETAIL") @Expose var detail: Int? = null

    )

}

