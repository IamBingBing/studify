package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class matchMenuVM @Inject constructor(application: Application, private val matchRepository: MatchRepository): ViewModel(){
    var ismentormatch = mutableStateOf(false);
    var isfastmatch = mutableStateOf(false);
    init {
        ismatching()
        ismentormatching()
    }
    fun ismatching() = matchRepository.ismatching()
        .subscribe({ it ->
            if (it.resultCode == "200"){
                isfastmatch.value= it.result
            }
        })
    fun ismentormatching() = matchRepository.ismentormatching()
        .subscribe({ it ->
            if (it.resultCode == "200"){
                ismentormatch.value= it.result
            }
        })
}