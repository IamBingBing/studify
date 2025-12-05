package com.example.studify.ui

import android.app.Application
import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import com.example.studify.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalMaterial3Api
class matchingOptionGroupVM @Inject constructor(application: Application, private val matchRepository: MatchRepository): ViewModel(){
    var days = mutableStateMapOf<String,Boolean>("월" to false,"화" to false,"수" to false,"목" to false,"금" to false,"토" to false,"일" to false)
    var purpose = mutableStateOf("")
    val tendency = mutableStateOf(0f)
    var matchcomplete = mutableStateOf(false)
    var nogroup = mutableStateOf(false)
    fun requestmatch(pp :String= purpose.value, td : String= tendency.value.toString()) = matchRepository.requestGroupMatch(pp,td)
        .subscribe({
                it->
            if (it.resultCode =="200" ){
                if (it.result =="false"){
                    nogroup.value= true;
                }
                else {
                    matchcomplete.value = true;
                }
            }
        },{error-> Log.e("mentor",error.toString())
        })
}