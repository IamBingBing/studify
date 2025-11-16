package com.example.studify.ui

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify.Tool.BaseModifiers
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun register(vm: registerVM = viewModel(), navController: NavController) {
    val BaseBtnModifier = Modifier.padding(10.dp)
    val RawModifier = BaseModifiers.BaseModifier.padding(top = 10.dp)
    val BaseTextfillModifier = Modifier.padding(10.dp)
    var email by vm.email
    val scrollState = rememberScrollState()
    var sex by vm.sex
    var adress by vm.adress
    var username by vm.username
    var userid by vm.userid
    var pw by vm.pw
    var repw by vm.repw
    val expanded = vm.expanded.value == 1
    val genderOptions by vm.genderOptions



    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = BaseModifiers.BaseModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("회원가입")
                TextField(
                    modifier = BaseTextfillModifier,
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("한경대 이메일") },
                    singleLine = true
                )

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
                    singleLine = true
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
                    onExpandedChange = { isExpanded ->
                        vm.onExpandedChange(if (isExpanded) 1 else 0) },
                    modifier = BaseTextfillModifier
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        IconButton (onClick = {
                            vm.onExpandedChange(if (expanded) 0 else 1)   // toggle
                        }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More options")
                        }

                        DropdownMenu (
                            expanded = expanded,
                            onDismissRequest = { vm.onExpandedChange(0) }
                        ) {

                            DropdownMenuItem(
                                text = { Text("남") },
                                onClick = {
                                    vm.onSexSelected(0)    // 남자 = 0
                                    vm.onExpandedChange(0) // 닫기
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("여") },
                                onClick = {
                                    vm.onSexSelected(1)    // 여자 = 1
                                    vm.onExpandedChange(0)
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
                    onClick = {

                    }
                ) { Text("가입하기") }
            }
        }
    }
}}

