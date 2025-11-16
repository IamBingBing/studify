package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.R
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class productDetailVM @Inject constructor(application: Application, studifyService: StudifyService) : ViewModel() {

    var productName = mutableStateOf("스타벅스 기프티콘")
    var productImageRes = mutableStateOf(R.drawable.statbucks1)
    var productPrice = mutableStateOf(300)
    var productDescription = mutableStateOf("스타벅스에서 사용 가능한 기프티콘 입니다.")


    /* 상품 상세 불러오기 */
    fun loadProduct(id: Int) {

    }

    /* 구매 요청  */
    fun requestBuy (
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {}
    ) {

    }

}
