package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
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
fun matchingOptionMentor (vm: matchingOptionMentorVM = viewModel(), navController: NavController){
    var expanded = false
    var expanded2 = false
    var wantLearn by vm.wantlearn
    var wantTeach by vm.wantteach


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