@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studify.Tool.BaseModifiers
import com.example.studify.Tool.MatchingCase
import java.time.LocalTime

@Composable
fun matchingOption (vm : matchingOptionVM = viewModel(), Case : String){
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var purpose by remember { mutableStateOf("토익") }
    var wantLearn by remember { mutableStateOf("") }
    var wantTeach by remember { mutableStateOf("") }

    val startTime = rememberTimePickerState(initialHour = LocalTime.now().hour, initialMinute = LocalTime.now().minute, is24Hour = true)
    val endTime = rememberTimePickerState(initialHour = 23, initialMinute = 59,is24Hour = true)
    val tendency by remember {mutableStateOf<SliderState>(SliderState(0f))}
    if (Case == MatchingCase.fast){
        Column {
            Text("번개매칭")
            Box(BaseModifiers.BaseBoxModifier) {
                Column() {
                    Text("시작할 시간")
                    TimeInput(state = startTime)
                }

            }
            Box(BaseModifiers.BaseBoxModifier) {
                Column() {
                    Text("끝낼 시간")
                    TimeInput(state = endTime)
                }

            }
            Button(onClick = {} , modifier = BaseModifiers.BaseBtnModifier) {
                Text("매칭 시작")
            }
        }
    }
    else if (Case == MatchingCase.group){
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
                    Text("가능한 날짜")
                    Row() {
                        Button(onClick = {}) { Text("월") }
                        Button(onClick = {}) { Text("화") }
                        Button(onClick = {}) { Text("수") }
                        Button(onClick = {}) { Text("목") }
                        Button(onClick = {}) { Text("금") }
                        Button(onClick = {}) { Text("토") }
                        Button(onClick = {}) { Text("일") }
                    }
                }
            }
            Button(onClick = {} , modifier = BaseModifiers.BaseBtnModifier) {
                Text("매칭 시작")
            }
        }
    }
    else if (Case == MatchingCase.exchange){
        Column() {
            Box(BaseModifiers.BaseBoxModifier) {
                Column() {
                    Text("배우고 싶은 과목")

                    Button(onClick = { expanded = true }) {
                        Text(wantLearn)
                    }


                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        List(10, init = { it.toString() }).forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    wantLearn = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }

            }

            Box(BaseModifiers.BaseBoxModifier) {
                Column() {
                    Text("알려줄 수 있는 과목")

                    Button(onClick = { expanded2 = true }) {
                        Text(wantTeach)
                    }


                    DropdownMenu(expanded = expanded2, onDismissRequest = { expanded2 = false }) {
                        List(10, init = { it.toString() }).forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    wantTeach = item
                                    expanded2 = false
                                }
                            )
                        }
                    }
                }

            }
            Box(BaseModifiers.BaseBoxModifier) {
                Column() {
                    Text("가능한 날짜")
                    Row() {
                        Button(onClick = {}) { Text("월") }
                        Button(onClick = {}) { Text("화") }
                        Button(onClick = {}) { Text("수") }
                        Button(onClick = {}) { Text("목") }
                        Button(onClick = {}) { Text("금") }
                        Button(onClick = {}) { Text("토") }
                        Button(onClick = {}) { Text("일") }
                    }
                }
            }
            Button(onClick = {}, modifier = BaseModifiers.BaseBtnModifier) {
                Text("매칭 시작")
            }
        }
    }
}