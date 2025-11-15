package com.example.studify.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalMaterial3Api
@Composable
fun matchingOptionFast (vm: matchingOptionFastVM = viewModel(), navController: NavController){
    var endTime = vm.endTime
    var startTime = vm.startTime
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
