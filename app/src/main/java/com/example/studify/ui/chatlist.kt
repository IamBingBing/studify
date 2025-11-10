package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studify.Tool.BaseModifiers

@Composable
fun chatlist(vm : chatlistVM = viewModel() , chatlist : List<String>){
    Column(modifier= BaseModifiers.BaseBoxModifier) {
        Box(BaseModifiers.BaseBoxModifier) { Text("채팅방") }

        LazyColumn(modifier = BaseModifiers.BaseBoxModifier) {
            items(chatlist) { chatname ->
                Text(text = chatname, modifier= BaseModifiers.BaseChatModifier)
            }
        }
    }
}