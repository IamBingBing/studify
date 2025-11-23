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

    /** 상점 목록 조회 */
    fun requestShop(): Single<ShopModel> {
        val param = HashMap<String, String>()
        return studifyService.requestShopData(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /** 상품 상세 조회 */
    fun requestProductDetail(goodId: Int): Single<ShopModel> {
        val param = HashMap<String, String>()
        param["GOOD_ID"] = goodId.toString()

        return studifyService.requestProductDetail(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
