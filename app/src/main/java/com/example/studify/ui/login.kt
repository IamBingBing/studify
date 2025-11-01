package com.example.studify.ui


import com.example.studify.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studify.Tool.BaseModifiers

@Composable
@Preview
fun login(vm : loginVM = viewModel() ) {
    var loginid by remember { mutableStateOf<String>("") }
    var password by remember { mutableStateOf<String>("") }
    var autologin by remember { mutableStateOf<Boolean>(false)  }
    Box (modifier = BaseModifiers.BaseBoxModifier) {
        Column(
            modifier = BaseModifiers.BaseBoxModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
        Image(modifier = BaseModifiers.BaseModifier.size(400.dp,400.dp),painter = painterResource(id = R.drawable.logo) , contentDescription = null)
        Box(modifier = BaseModifiers.BaseModifier.size(280.dp, 200.dp).align(alignment = Alignment.CenterHorizontally)) {
            Column(
                modifier = BaseModifiers.BaseBoxModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                TextField(
                    value = loginid,
                    onValueChange = { loginid = it },
                    label = { Text("ID") },
                    singleLine = true
                )
                TextField(
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
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = { vm.requestLogin() },
                        enabled = true,
                        modifier = BaseModifiers.BaseBtnModifier
                    ) {
                        Text(
                            "로그인"
                        )
                    }
                }

            }
        }
}
    }
}