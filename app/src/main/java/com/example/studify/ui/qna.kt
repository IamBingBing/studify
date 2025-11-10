package com.example.studify.ui

import android.view.Surface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studify.Tool.BaseModifiers

@Composable
fun qna(vm : qnaVM = viewModel()){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White){
        Column(modifier = BaseModifiers.BaseModifier.fillMaxSize()) {
            LazyColumn(modifier = BaseModifiers.BaseModifier.fillMaxSize()){
                items(20){
                        index ->
                    Text("메시지 ${index + 1}", modifier = BaseModifiers.BaseModifier.padding(vertical = 4.dp))

                }
            }
            Row(modifier = BaseModifiers.BaseModifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = BaseModifiers.BaseModifier.weight(1f),
                    placeholder = { Text("질문을 입력하세요") }
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "전송"
                    )
                }
            }

        }
    }

}