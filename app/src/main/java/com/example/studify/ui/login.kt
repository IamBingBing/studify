package com.example.studify.ui


import android.annotation.SuppressLint
import com.example.studify.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import androidx.hilt.navigation.compose.hiltViewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun login(vm : loginVM = hiltViewModel(), navController: NavController) {
    var loginid by vm.loginid
    var password by vm.password
    var autologin by vm.autologin
    var loginerror by vm.loginerror
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
            onDismissRequest = { loginerror =""},
            title = { Text("로그인 실패") },
            text = { Text(loginerror?: "") },
            confirmButton = {
                TextButton (onClick = { loginerror=""}) {
                    Text("확인")
                }
            }
        )
    }
    Scaffold (modifier = BaseModifiers.BaseBoxModifier) {
        padding->
        Column(
            modifier = BaseModifiers.BaseBoxModifier.padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
        Image(modifier = BaseModifiers.BaseModifier.size(400.dp, 400.dp),painter = painterResource(id = R.drawable.logo1) , contentDescription = null)
        Box(modifier = BaseModifiers.BaseModifier
            .size(280.dp, 280.dp)
            .align(alignment = Alignment.CenterHorizontally)) {
            Column(
                modifier = BaseModifiers.BaseBoxModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
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
                        Text(
                            "로그인"
                        )
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