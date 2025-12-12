package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.R
import com.example.studify.data.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class productDetailVM @Inject constructor(
    application: Application,
    private val shopRepository: ShopRepository
) : ViewModel() {

    var productName = mutableStateOf("")
    var productImageRes = mutableStateOf(R.drawable.img_0) //기본 이미지
    var productPrice = mutableStateOf(0)
    var productDescription = mutableStateOf("")

    private val disposables = CompositeDisposable()

    private fun mapImageRes(goodId: Int): Int {
        return when (goodId) {
            1 -> R.drawable.img_1
            2 -> R.drawable.img_2
            3 -> R.drawable.img_3
            4 -> R.drawable.img_4
            5 -> R.drawable.img_5
            6 -> R.drawable.img_6
            7 -> R.drawable.img_7
            else -> R.drawable.img_0
        }
    }

    /** 상품 상세 불러오기 */
    fun loadProduct(goodId: Int) {

        // 먼저 이미지부터 세팅 (API 느려도 화면에 바로 뜨게)
        productImageRes.value = mapImageRes(goodId)

        val d = shopRepository.requestProductDetail(goodId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ model ->

                if (model.resultCode == "200" &&
                    model.result != null &&
                    model.result!!.isNotEmpty()
                ) {
                    val item = model.result!![0]

                    productName.value = item.goodName ?: ""
                    productPrice.value = item.price ?: 0
                    productDescription.value = item.detail ?: ""

                    //  서버에 이미지 값이 생기면 여기서 교체 가능
                    // productImageRes.value = ...
                } else {
                    productName.value = "상품 정보를 불러오지 못했습니다."
                    productDescription.value = model.errorMsg ?: ""
                    productImageRes.value = mapImageRes(goodId)
                }

            }, { e ->
                productName.value = "에러 발생"
                productDescription.value = e.message ?: ""
                productImageRes.value = mapImageRes(goodId)
            })

        disposables.add(d)
    }

    /** 구매 요청 */
    fun requestBuy(
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {}
    ) {
        // TODO: 구매 API 붙이면 여기에 호출
        onSuccess()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
