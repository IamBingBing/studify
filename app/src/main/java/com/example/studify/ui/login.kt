package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize // fillMaxSize 추가 권장
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding // 키보드 패딩용
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState // 스크롤 상태용
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll // 스크롤 기능용
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.R
import com.example.studify.Tool.BaseModifiers

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun login(vm: loginVM = hiltViewModel(), navController: NavController) {
    var loginid by vm.loginid
    var password by vm.password
    var autologin by vm.autologin
    var loginerror by vm.loginerror

    // 스크롤 상태를 기억하는 변수 선언
    val scrollState = rememberScrollState()

    if (vm.loginsuccess.value) {
        navController.navigate("grouplist") {
            popUpTo("login") {
                vm.loginsuccess.value = false
                inclusive = true
            }
        }
    }
    if (loginerror != "") {
        AlertDialog(
            onDismissRequest = { loginerror = "" },
            title = { Text("로그인 실패") },
            text = { Text(loginerror ?: "") },
            confirmButton = {
                TextButton(onClick = { loginerror = "" }) {
                    Text("확인")
                }
            }
        )
    }

    Scaffold(modifier = BaseModifiers.BaseBoxModifier) { padding ->
        Column(
            modifier = BaseModifiers.BaseBoxModifier
                .padding(padding)
                .fillMaxSize() // 전체 화면을 차지하도록 설정 (스크롤을 위해 중요)
                .verticalScroll(scrollState) // 1. 드래그(스크롤) 기능 추가
                .imePadding(), // 2. 키보드가 올라올 때 화면을 위로 밀어 올리는 기능
            verticalArrangement = Arrangement.Center,
<<<<<<< Updated upstream
<<<<<<< Updated upstream
            horizontalAlignment = Alignment.End // 기존 코드 유지 (전체 Column 정렬)
        ) {
            // 이미지 등 내용물
=======
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
=======
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
>>>>>>> Stashed changes
        Image(modifier = BaseModifiers.BaseModifier.size(350.dp, 350.dp),painter = painterResource(id = R.drawable.logo3) , contentDescription = null)
        Box(modifier = BaseModifiers.BaseModifier
            .size(280.dp, 280.dp)
            .align(alignment = Alignment.CenterHorizontally)) {
>>>>>>> Stashed changes
            Column(
                modifier = Modifier.fillMaxWidth(), // 내부 정렬을 위해 너비 채움
                horizontalAlignment = Alignment.CenterHorizontally // 이미지 중앙 정렬
            ) {
                Image(
                    modifier = BaseModifiers.BaseModifier.size(400.dp, 400.dp),
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = null
                )
            }

            Box(
                modifier = BaseModifiers.BaseModifier
                    .size(280.dp, 280.dp)
                    // Box 자체를 부모 Column 기준으로 중앙 정렬하려면 부모의 alignment 설정을 따라야 합니다.
                    // 현재 부모 Column이 Alignment.End이므로, 필요시 Modifier.align(Alignment.CenterHorizontally) 추가
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = BaseModifiers.BaseBoxModifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    // textField는 소문자로 되어있는데, 커스텀 함수가 아니라면 TextField로 대문자로 써야 할 수 있습니다.
                    // (사용자 코드 그대로 유지함)
                    textField(
                        value = loginid,
                        onValueChange = { loginid = it },
                        label = { Text("ID") },
                        singleLine = true
                    )
                    textField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Row(
                        modifier = BaseModifiers.BaseModifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("자동 로그인")
                        Checkbox(
                            checked = autologin,
                            onCheckedChange = { autologin = it },
                        )
                        IconButton(onClick = {}) { }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = { vm.requestlogin() },
                            enabled = true,
                            modifier = BaseModifiers.BaseBtnModifier
                        ) {
                            Text("로그인")
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "계정이 없으신가요? ",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "회원가입",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.clickable {
                                navController.navigate("register")
                            }
                        )
                    }
                }
            }
        }
    }
}