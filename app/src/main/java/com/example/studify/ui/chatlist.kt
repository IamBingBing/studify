package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
fun chatlist(vm : chatingRoomVM = hiltViewModel() ,  navController: NavController){
    var chatlist = listOf("")
    Scaffold (topBar = { Text("채팅방") }, bottomBar= { navigationbar(navController) }){
        innerpadding->
        Column(modifier= BaseModifiers.BaseBoxModifier.padding(innerpadding).fillMaxSize()) {
            Box(BaseModifiers.BaseBoxModifier) { Text("채팅방") }

            LazyColumn(modifier = BaseModifiers.BaseBoxModifier) {
                items(chatlist) { chatname ->
                    Text(text = chatname, modifier= BaseModifiers.BaseChatModifier)
                }
            }
        }
    }
}