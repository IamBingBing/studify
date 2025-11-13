package com.example.studify.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
@Preview
fun mypage(vm : mypageVM = viewModel() , navController: NavController) {

    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("강명묵") }
    var email by remember { mutableStateOf("my@naver.com") }
    var group by remember { mutableStateOf("A 그룹") }
    var sex by remember { mutableStateOf("남자") }
    var address by remember { mutableStateOf("경기도 파주시") }
    var tendency by remember { mutableStateOf("여유로움") }
    var point by remember { mutableStateOf("1000P") }

    Column(
        modifier = BaseModifiers.BaseBoxModifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "마이 페이지",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (isEditing) {

            EditProfileField(label = "이름", value = name, onValueChange = { name = it })
            EditProfileField(label = "이메일", value = email, onValueChange = { email = it })
            EditProfileField(label = "주소", value = address, onValueChange = { address = it })
            EditProfileField(label = "성별", value = sex, onValueChange = { sex = it })

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { isEditing = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("저장하기")
            }

        } else {
            DisplayProfileField(label = "이름", value = name)
            DisplayProfileField(label = "이메일", value = email)
            DisplayProfileField(label = "소속 그룹", value = group)
            DisplayProfileField(label = "성별", value = sex)
            DisplayProfileField(label = "주소", value = address)
            DisplayProfileField(label = "성향 점수", value = tendency)
            DisplayProfileField(label = "포인트", value = point)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { isEditing = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("정보 수정")
            }
        }
    }
}

@Composable
public fun DisplayProfileField(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}

@Composable
public fun EditProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        singleLine = true
    )
}
