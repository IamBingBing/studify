package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
fun mypage(vm: mypageVM = hiltViewModel(), navController: NavController) {

    LaunchedEffect(Unit) {
        vm.refreshMyInfoFromServer()
    }

    val isEditing by vm.isEditing
    var name by vm.name
    var email by vm.email
    var group by vm.group
    var sex by vm.sex
    var address by vm.address
    var point by vm.point

    Scaffold(
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->

        Column(
            modifier = BaseModifiers.BaseBoxModifier
                .fillMaxSize()
                .padding(innerPadding)
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
                EditProfileField(label = "주소", value = address, onValueChange = { address = it })
                EditProfileField(
                    label = "성별 (남자/여자)",
                    value = sex,
                    onValueChange = { sex = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { vm.saveUserInfo() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("저장하기")
                }

                TextButton(
                    onClick = { vm.isEditing.value = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("취소")
                }

            } else {
                DisplayProfileField(label = "이름", value = name)
                DisplayProfileField(label = "이메일", value = email)
                val groupDisplay = if (group == "[]" || group.isEmpty()) "없음" else group
                DisplayProfileField(label = "소속 그룹", value = groupDisplay)
                DisplayProfileField(label = "성별", value = sex)
                DisplayProfileField(label = "주소", value = address)
                DisplayProfileField(label = "포인트", value = point)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { vm.isEditing.value = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("정보 수정")
                }
            }
        }
    }
}

@Composable
fun DisplayProfileField(label: String, value: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                modifier = Modifier.width(100.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = value,
                fontSize = 16.sp
            )
        }
        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

@Composable
fun EditProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        singleLine = true
    )
}