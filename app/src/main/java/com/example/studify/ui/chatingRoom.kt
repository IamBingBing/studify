package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
fun chatingRoom(vm: chatingRoomVM = viewModel() , navController: NavController) {
    var chat =  mapOf ("a" to "a")
    LazyColumn(modifier= BaseModifiers.BaseBoxModifier) {
        items(chat.entries.toList()){ entries ->
            Column(modifier= BaseModifiers.BaseModifier.padding(vertical = 5.dp)) {
                Text(entries.key, modifier= BaseModifiers.BaseModifier.height(15.dp).padding(horizontal = 20.dp))
                Text(entries.value, modifier= BaseModifiers.BaseChatModifier.height(33.dp).padding(horizontal = 20.dp), fontSize = 25.sp )

            }
        }
    }
}