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
import androidx.compose.material3.Button
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
import androidx.compose.material3.MenuAnchorType


@OptIn(ExperimentalMaterial3Api::class) // <-- 이 한 줄을 추가합니다.
@Composable
@Preview
fun register() {
    val BaseBtnModifier = Modifier.padding(10.dp)
    val RawModifier = BaseModifiers.BaseModifier.padding(top = 10.dp)
    val BaseTextfillModifier = Modifier.padding(10.dp)
    var email by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var sex by remember { mutableStateOf("") }
    var adress by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var userid by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var repw by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("남", "여")

    Surface(color = Color.White,
        modifier = Modifier.fillMaxSize()) {
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
                onExpandedChange = { expanded = !expanded },
                modifier = BaseTextfillModifier
            ) {
                TextField(
                    modifier = BaseTextfillModifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
                    value = sex,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("성별") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    genderOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                sex = selectionOption
                                expanded = false
                            }
                        )
                    }
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
            ) { Text("가입하기")}
        }
    }
}
