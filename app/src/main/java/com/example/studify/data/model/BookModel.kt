package com.example.studify.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BookModel(
    @SerializedName("result_code") @Expose var resultCode: String = "",
    @SerializedName("error_msg") @Expose var errorMsg: String = "",
    @SerializedName("result") @Expose var result: List<BookItem>? = null
) {
    data class BookItem(
        @SerializedName("title") @Expose var title: String = "",
        @SerializedName("author") @Expose var author: String = "",
        @SerializedName("desc") @Expose var description: String = ""
    )
    data class BookInfo(
        @SerializedName("title") @Expose var title: String? = null,
        @SerializedName("author") @Expose var author: String? = null,
        @SerializedName("publisher") @Expose var publisher: String? = null,
        @SerializedName("pub_year") @Expose var pubYear: String? = null,
        @SerializedName("isbn") @Expose var isbn: String? = null
    )
}