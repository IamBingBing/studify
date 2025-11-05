package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun qna(vm : qnaVM = viewModel()){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White){}

}