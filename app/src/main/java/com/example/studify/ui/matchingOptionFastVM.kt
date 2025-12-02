package com.example.studify.ui

import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.Tool.Preferences
import com.example.studify.data.StudifyService
import com.example.studify.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

@ExperimentalMaterial3Api
@HiltViewModel
class matchingOptionFastVM @Inject constructor(application: Application, private val matchRepository: MatchRepository): ViewModel() {
    var endTime = TimePickerState(initialHour = 23, initialMinute = 59,is24Hour = true)
    var startTime = TimePickerState(initialHour = LocalTime.now().hour, initialMinute = LocalTime.now().minute, is24Hour = true)
    var match = mutableStateOf(false)
    var start = startTime.hour*60 + startTime.minute
    var end = endTime.hour*60+endTime.minute
    var matchingcomplete = mutableStateOf(false)
    init {
        ismatching();
    }
    fun ismatching() = matchRepository.ismatching()
        .subscribe({ it ->
            if (it.resultCode == "200"){
                match.value= it.result
            }
        })
    fun matchstart(startt:Int = start, endd :Int=end)= matchRepository.requestFastMatch(startt , endd)
        .subscribe({
            result->
            if(result.resultCode == "201" ){

            }
        },{

        })
}