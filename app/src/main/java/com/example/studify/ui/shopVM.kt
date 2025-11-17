package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.Tool.Preferences
import com.example.studify.data.StudifyService
import com.example.studify.data.model.shopModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class shopVM @Inject constructor( appliction: Application , studifyService: StudifyService) : ViewModel(){
    var detail = mutableStateOf("")
    var good_ID = mutableStateOf<Int>(0)
    var price = mutableStateOf<Int>(0)
    var items = mutableStateMapOf<String, Int>( "편의점 5000원 쿠폰" to 500,
        "BHC치킨 10000원 쿠폰" to 500)
    var goodname = mutableStateOf("")


}


/*private requestAnnouce ( groupid )<model> {

    model.result.foreach(){
        it -> it[0]
        if ( it.result.is_pin  )
        notices.add(it.result.announcename , it.result.is_pin)
    }
}
*/

