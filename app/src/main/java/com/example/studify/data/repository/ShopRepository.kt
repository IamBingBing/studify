package com.example.studify.data.repository

import com.example.studify.data.StudifyService
import com.example.studify.data.model.ShopModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopRepository @Inject constructor(private val studifyService: StudifyService) {
    fun requestShop() : Single<ShopModel> {
        val param = HashMap<String, String>().apply {

        }
        return studifyService.requestShopData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}