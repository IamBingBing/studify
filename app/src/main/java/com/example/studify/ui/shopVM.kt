package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.model.ShopModel
import com.example.studify.data.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class shopVM @Inject constructor(
    application: Application,
    private val shopRepository: ShopRepository
) : ViewModel() {

    // 상품 리스트
    val items = mutableStateListOf<ShopModel.shopResult>()

    // 로딩 / 에러 상태
    val isLoading = mutableStateOf(false)
    val errorMsg  = mutableStateOf<String?>(null)

    private val disposables = CompositeDisposable()

    init {
        requestShop()
    }

    fun requestShop() {
        isLoading.value = true
        errorMsg.value = null

        val d = shopRepository.requestShop()
            .subscribe({ model ->
                isLoading.value = false

                if (model.resultCode == "200" && model.result != null) {
                    items.clear()
                    items.addAll(model.result!!)
                } else {
                    errorMsg.value = model.errorMsg
                }
            }, { e ->
                isLoading.value = false
                errorMsg.value = e.message
            })

        disposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
