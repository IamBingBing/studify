package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import com.example.studify.Tool.Preferences

@Composable
fun chatingRoom(vm: chatingRoomVM = hiltViewModel() , navController: NavController) {
    var message = vm.message
    var sendmessage by vm.sendmessage
    val name = Preferences.getString("USERNAME")
    Scaffold(bottomBar = { navigationbar(navController) }) {padding ->
        Column (modifier = BaseModifiers.BaseModifier.padding(padding)){
            LazyColumn(modifier = BaseModifiers.BaseBoxModifier) {
                items(message.value){ entries ->

                        Column(
                            modifier = BaseModifiers.BaseModifier.padding(vertical = 5.dp)
                                .fillMaxWidth()
                        ) {
                            if (name== entries.CHATNAME) {
                                Text(
                                    entries.CHAT,
                                    modifier = BaseModifiers.BaseChatModifier.height(33.dp)
                                        .padding(horizontal = 20.dp),
                                    fontSize = 25.sp
                                )
                            } else {

                                Text(
                                    entries.CHATNAME,
                                    modifier = BaseModifiers.BaseModifier.height(15.dp)
                                        .padding(horizontal = 20.dp)
                                )
                                Text(
                                    entries.CHAT,
                                    modifier = BaseModifiers.BaseChatModifier.height(33.dp)
                                        .padding(horizontal = 20.dp),
                                    fontSize = 25.sp
                                )
                            }
                        }
                    }

            }
            Row {
                TextField(value = "", onValueChange = {sendmessage = it})
                Button(onClick = { vm.sendMessage() }) {
                    Icon(Icons.Default.Check, contentDescription = null)
                }
            }
        }

    }
}