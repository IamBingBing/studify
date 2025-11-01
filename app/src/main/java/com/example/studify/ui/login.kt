package com.example.studify.ui


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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.w3c.dom.Text

@Composable
@Preview
fun login(vm : loginVM = viewModel()) {
    var loginid by remember { mutableStateOf<String>("") }
    var password by remember { mutableStateOf<String>("") }
    Column {
        Text (text = "ID")
        TextField(value = loginid, onValueChange = { loginid = it} ,label = { Text("ID") }, singleLine = true)
        Text (text = "Password")
        TextField(
            value = password,
            onValueChange = { password = it} ,
            label = { Text("Password")},
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }

}