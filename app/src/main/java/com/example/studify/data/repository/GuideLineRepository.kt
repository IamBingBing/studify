package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.BookModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GuideLineRepository @Inject constructor(
    private val studifyService: StudifyService
) {

    fun getRecommendations(keyword: String): Single<BookModel> {
        val param = HashMap<String, String>().apply {
            this["KEYWORD"] = keyword
        }
        return studifyService.requestGuideBooks(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
