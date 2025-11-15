package com.example.studify.ui

import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalMaterial3Api
class matchingOptionGroupVM @Inject constructor(application: Application, studifyService: StudifyService): ViewModel(){
    var days = mutableStateMapOf<String,Boolean>("월" to false,"화" to false,"수" to false,"목" to false,"금" to false,"토" to false,"일" to false)
    var purpose = mutableStateOf("토익")
    val tendency = mutableStateOf<SliderState>(SliderState(0f))
}