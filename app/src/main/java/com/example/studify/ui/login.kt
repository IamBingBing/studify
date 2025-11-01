package com.example.studify.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studify.BaseModifier

@Composable
@Preview
fun login(vm : loginVM = viewModel()) {
    var loginid by remember { mutableStateOf<String>("") }
    var password by remember { mutableStateOf<String>("") }
    Column ( modifier = BaseModifier,verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally ){
        TextField(
            value = loginid,
            onValueChange = { loginid = it } ,
            label = { Text("ID") },
            singleLine = true
        )
        TextField(
            value = password,
            onValueChange = { password = it} ,
            label = { Text("Password")},
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button( onClick = { vm.requestLogin()} , enabled = true, modifier = Modifier.align()) { Text("로그인") }
    }

}