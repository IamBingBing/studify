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
    var productImageRes = mutableStateOf(R.drawable.ic_launcher_background)
    var productPrice = mutableStateOf(0)
    var productDescription = mutableStateOf("")

    private val disposable = CompositeDisposable()

    /** 상품 상세 불러오기 */
    fun loadProduct(goodId: Int) {

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

                    // 이미지가 서버에서 오면 이 부분에서 설정 예정
                    productImageRes.value = R.drawable.ic_launcher_background

                } else {
                    productName.value = "상품 정보를 불러오지 못했습니다."
                    productDescription.value = model.errorMsg
                }

            }, { e ->
                productName.value = "에러 발생"
                productDescription.value = e.message ?: ""
            })

        disposable.add(d)
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
        disposable.clear()
    }
}
