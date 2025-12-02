package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.studify.data.StudifyService
import com.example.studify.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class matchingOptionMentorVM @Inject constructor(application: Application,private val matchRepository: MatchRepository):ViewModel(){
    var wantlearn = mutableStateOf("")
    var wantteach = mutableStateOf("")
    var matchcomplete = mutableStateOf(false)
    fun requesetmatch(wl : String = wantlearn.value, wt : String = wantteach.value) = matchRepository.requestMentorMatch(wl,wt)
        .subscribe({
            it->
            if (it.resultCode =="200"){
                matchcomplete.value = true;
            }
        })
}