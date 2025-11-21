package com.example.studify.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class chatlistVM  @Inject constructor(application: Application, private val chatRepository: ChatRepository): ViewModel() {

}