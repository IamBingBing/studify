package com.example.studify.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun register(
    vm: registerVM = hiltViewModel(),
    navController: NavController
) {
    val BaseBtnModifier = Modifier.padding(10.dp)
    val BaseTextfillModifier = Modifier.padding(10.dp)


    var email by vm.email
    var sex by vm.sex
    var adress by vm.adress
    var username by vm.username
    var userid by vm.userid
    var pw by vm.pw
    var repw by vm.repw
    val expanded by vm.expanded
    val isSuccess by vm.registerSuccess.collectAsState()
var authCode by vm.authcode
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = BaseModifiers.BaseModifier
                    .verticalScroll(scrollState)
                    .padding(vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "회원가입",
                    fontSize = 22.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(modifier = BaseTextfillModifier) {
                    TextField(
                        modifier = BaseTextfillModifier,
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("한경대 이메일") },
                        singleLine = true
                    )
                    Button(onClick = {}) { Text("인증번호 받기") }
                }

                Row(modifier = BaseTextfillModifier) {
                    TextField(
                        modifier = BaseTextfillModifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        value = authCode,
                        onValueChange = { authCode = it },
                        label = { Text("인증번호 입력칸") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Button(onClick = {}) { Text("인증번호 확인하기") }
                }

                TextField(
                    modifier = BaseTextfillModifier,
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("이름") },
                    singleLine = true
                )

                TextField(
                    modifier = BaseTextfillModifier,
                    value = userid,
                    onValueChange = { userid = it },
                    label = { Text("아이디") },
                    singleLine = true
                )

                TextField(
                    modifier = BaseTextfillModifier,
                    value = pw,
                    onValueChange = { pw = it },
                    label = { Text("비밀번호") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                TextField(
                    modifier = BaseTextfillModifier,
                    value = repw,
                    onValueChange = { repw = it },
                    label = { Text("비밀번호 재등록") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { vm.onExpandedChange(!expanded) },
                    modifier = BaseTextfillModifier
                ) {

                    TextField(
                        value = when (sex) {
                            0 -> "남"
                            1 -> "여"
                            else -> "성별 선택"
                        },
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("성별") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = BaseModifiers.BaseModifier
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { vm.onExpandedChange(false) }
                    ) {
                        DropdownMenuItem(
                            text = { Text("남") },
                            onClick = {
                                vm.onSexSelected(0)
                                vm.onExpandedChange(false)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("여") },
                            onClick = {
                                vm.onSexSelected(1)
                                vm.onExpandedChange(false)
                            }
                        )
                    }
                }


                TextField(
                    modifier = BaseTextfillModifier,
                    value = adress,
                    onValueChange = { adress = it },
                    label = { Text("주소") },
                    singleLine = true
                )

                Button(
                    modifier = BaseBtnModifier,
                    onClick = {
                        vm.register()

                    }
                ) {
                    Text("가입하기")
                }
                LaunchedEffect(isSuccess) {
                    if (isSuccess) {
                        navController.navigate("grouplist")
                    }
                }
            }
        }
    }
}