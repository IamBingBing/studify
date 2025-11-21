package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
fun chatlist(vm : chatingRoomVM = hiltViewModel() ,  navController: NavController){
    var error = vm.error
    var chatlist = vm.groupids
    if (error.value != "") {
        AlertDialog(
            onDismissRequest = { error.value =""},
            title = { Text("오류") },
            text = { Text(error.value?: "") },
            confirmButton = {
                TextButton (onClick = { error.value=""}) {
                    Text("확인")
                }
            }
        )
    }
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