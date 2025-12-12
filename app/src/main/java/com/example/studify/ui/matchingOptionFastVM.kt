package com.example.studify.ui

import android.app.Application
import android.icu.text.SimpleDateFormat
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
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@ExperimentalMaterial3Api
@HiltViewModel
class matchingOptionFastVM @Inject constructor(application: Application, private val matchRepository: MatchRepository): ViewModel() {
    var endTime = TimePickerState(initialHour = 23, initialMinute = 59,is24Hour = true)
    var startTime = TimePickerState(initialHour = LocalTime.now().hour, initialMinute = LocalTime.now().minute, is24Hour = true)
    var match = mutableStateOf(false)
    var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    var start = formatter.format(Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY,startTime.hour,)
        set(Calendar.MINUTE, startTime.minute)
        set(Calendar.SECOND , 0)
    }.time)
    var end = formatter.format(Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY,endTime.hour,)
        set(Calendar.MINUTE, endTime.minute)
        set(Calendar.SECOND , 0)
    }.time)
    var matchingcomplete = mutableStateOf(false)


    fun matchstart(startt:String = start, endd :String=end)= matchRepository.requestFastMatch(startt , endd)
        .subscribe({
            result->
            if(result.resultCode == "201"  ){
                matchingcomplete.value = true;
                match.value = true;
            }
            else if(result.resultCode == "202"  ){
                match.value= true;
            }
        },{

        })
}