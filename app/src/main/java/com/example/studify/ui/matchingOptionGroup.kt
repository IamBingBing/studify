package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
@ExperimentalMaterial3Api
fun matchingOptionGroup(vm: matchingOptionGroupVM = viewModel(), navController: NavController){

    var expanded = false
    var expanded2 = false
    var purpose by vm.purpose
    var tendency by vm.tendency
    var days = vm.days
    Column() {
        Box(BaseModifiers.BaseBoxModifier) {
            Column() {
                Text("목적")

                Button(onClick = {expanded = true}) {
                    Text(purpose)
                }


                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    List(10, init = { it.toString() }).forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                purpose = item
                                expanded =false}
                        )
                    }
                }
            }
        }
        Box(BaseModifiers.BaseBoxModifier) {
            Column() {
                Text("그룹 성향")
                Slider(
                    state = tendency
                )
            }

        }
        Box(BaseModifiers.BaseBoxModifier) {
            Column() {
                Text("가능한 요일")
                Row() {
                    Button(onClick = {days["월"] = !days["월"]!! }) { Text("월") }
                    Button(onClick = {days["화"] = !days["화"]!! }) { Text("화") }
                    Button(onClick = {days["수"] = !days["수"]!! }) { Text("수") }
                    Button(onClick = {days["목"] = !days["목"]!! }) { Text("목") }
                    Button(onClick = {days["금"] = !days["금"]!! }) { Text("금") }
                    Button(onClick = {days["토"] = !days["토"]!! }) { Text("토") }
                    Button(onClick = {days["일"] = !days["일"]!! }) { Text("일") }
                }
            }
        }
        Button(onClick = {} , modifier = BaseModifiers.BaseBtnModifier) {
            Text("매칭 시작")
        }
    }
}