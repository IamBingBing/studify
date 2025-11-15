package com.example.studify.ui

import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime
import javax.inject.Inject

@ExperimentalMaterial3Api
@HiltViewModel
class matchingOptionFastVM @Inject constructor(application: Application, studifyService: StudifyService): ViewModel() {
    var endTime = TimePickerState(initialHour = 23, initialMinute = 59,is24Hour = true)
    var startTime = TimePickerState(initialHour = LocalTime.now().hour, initialMinute = LocalTime.now().minute, is24Hour = true)

}