package com.example.studify.ui

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.studify.data.StudifyService
import javax.inject.Inject

class  qnaVM  @Inject constructor(appliction: Application, studifyService: StudifyService) : ViewModel(){
    var qna_title =mutableStateOf<String>("")
    var qna_id =mutableStateOf<String>("")
    var qna_answer =mutableStateOf<String>("")
    var qna_content =mutableStateOf<String>("")

    var items = mutableStateMapOf<String, String>( "질문사항" to "질문")

}