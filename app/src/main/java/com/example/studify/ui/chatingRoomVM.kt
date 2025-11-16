package com.example.studify.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class chatingRoomVM @Inject constructor(application: Application, studifyService: StudifyService): ViewModel() {

}