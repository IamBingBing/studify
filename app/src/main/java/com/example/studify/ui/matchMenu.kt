package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
fun matchMenu (vm : matchMenuVM = viewModel(), navController: NavController){
    Scaffold (bottomBar = { navigationbar(navController) }) {
        innerpadding->
        Box(modifier = BaseModifiers.BaseModifier.padding(innerpadding).fillMaxSize()) {
            Row(modifier = BaseModifiers.BaseModifier.align(Alignment.Center)) {
                Button(modifier = BaseModifiers.BaseBtnModifier
                    .width(100.dp)
                    .height(200.dp), onClick = { vm.lightMatch() }) {
                    Text("번개\n매칭")
                }
                Button(modifier = BaseModifiers.BaseBtnModifier
                    .width(100.dp)
                    .height(200.dp), onClick = { vm.groupMatch() }) {
                    Text("그룹\n매칭")
                }
                Button(modifier = BaseModifiers.BaseBtnModifier
                    .width(100.dp)
                    .height(200.dp), onClick = { vm.knowledgeTrade() }) {
                    Text("지식\n교환")
                }
            }
        }
    }
}