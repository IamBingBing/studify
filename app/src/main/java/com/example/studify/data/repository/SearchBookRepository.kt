
package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.BookModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchBookRepository @Inject constructor(private val studifyService: StudifyService) {

    fun requestSearchBook(groupId: String): Single<BookModel> {
        val param = HashMap<String, String>()
        param["GROUPID"] = groupId

        return studifyService.requestSearchBook(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}