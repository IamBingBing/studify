@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.studify.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers  // 써도 되고 안 써도 되는데, 혹시 다른 데서 쓰면 유지

@Composable
fun register(
    vm: registerVM = hiltViewModel(),
    navController: NavController
) {
    var email by vm.email
    var authCode by vm.authcode
    var sex by vm.sex
    var adress by vm.adress
    var username by vm.username
    var userid by vm.userid
    var pw by vm.pw
    var repw by vm.repw

    val isSuccess by vm.registerSuccess.collectAsState()
    val isCodeSent by vm.isCodeSent
    val errorMsg by vm.registerError.collectAsState()

    val scrollState = rememberScrollState()   // ✅ 다시 추가
    val keyboardController = LocalSoftwareKeyboardController.current
    val accent = Color(0xFF4F46E5)

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFF9FAFB), Color(0xFFF9FAFB))
                    )
                )
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .verticalScroll(scrollState),     // ✅ 스크롤 가능하게 해서 키보드 위로 올릴 수 있게
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ===== 제목 =====
                Text(
                    text = "회원가입",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center     // ✅ 중앙 정렬
                )

                // ===== 입력 영역 카드 =====
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // ------- 학교 이메일 -------
                        ModernFieldLabel("학교 이메일", required = true)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                value = email,
                                onValueChange = { email = it },
                                singleLine = true,
                                // placeholder 조금 줄여서 보기 좋게
                                placeholder = { Text("예: abc@hknu.ac.kr") },
                                colors = inputColors()
                            )

                            OutlinedButton(
                                onClick = {
                                    if (email.isNotEmpty()) {
                                        vm.requestEmailCode(email)
                                        keyboardController?.hide()
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, accent),
                            ) {
                                Text(
                                    text = if (isCodeSent) "재전송" else "인증요청",
                                    fontSize = 13.sp,
                                    color = accent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // ------- 인증번호 -------
                        if (isCodeSent) {
                            ModernFieldLabel("인증번호", required = true)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextField(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 8.dp),
                                    value = authCode,
                                    onValueChange = { authCode = it },
                                    singleLine = true,
                                    placeholder = { Text("6자리 코드") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    colors = inputColors()
                                )
                                OutlinedButton(
                                    onClick = {
                                        vm.verifyEmailCode(email, authCode)
                                        keyboardController?.hide()
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, accent),
                                ) {
                                    Text(
                                        text = "확인",
                                        fontSize = 13.sp,
                                        color = accent,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            if (vm.isEmailVerified.value) {
                                Text(
                                    text = "인증되었습니다.",
                                    color = Color(0xFF16A34A),  // 초록색
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                                )
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        // ------- 아이디 -------
                        ModernFieldLabel("아이디", true)
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            value = userid,
                            onValueChange = { userid = it },
                            singleLine = true,
                            colors = inputColors()
                        )

                        // ------- 비밀번호 -------
                        ModernFieldLabel("비밀번호", true)
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            value = pw,
                            onValueChange = { pw = it },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            colors = inputColors()
                        )

                        // ------- 비밀번호 확인 -------
                        ModernFieldLabel("비밀번호 확인", true)
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            value = repw,
                            onValueChange = { repw = it },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            colors = inputColors()
                        )

                        // ------- 이름 -------
                        ModernFieldLabel("이름", true)
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            value = username,
                            onValueChange = { username = it },
                            singleLine = true,
                            colors = inputColors()
                        )

                        // ------- 주소 -------
                        ModernFieldLabel("주소", false)
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            value = adress,
                            onValueChange = { adress = it },
                            singleLine = true,
                            colors = inputColors()
                        )

                        // ------- 성별 -------
                        ModernFieldLabel("성별", true)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SelectionChip("남자", sex == 0, accent) { vm.onSexSelected(0) }
                            SelectionChip("여자", sex == 1, accent) { vm.onSexSelected(1) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (errorMsg != null && !vm.isEmailVerified.value) {
                    Text(
                        text = errorMsg!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }


                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { vm.register() }
                ) {
                    Text(
                        "가입하기",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LaunchedEffect(isSuccess) {
                    if (isSuccess) navController.navigate("login")
                }
            }
        }
    }
}

@Composable
private fun ModernFieldLabel(text: String, required: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 2.dp)
    ) {
        Text(
            text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF111827)
        )
        if (required) {
            Text(
                " *",
                fontSize = 13.sp,
                color = Color(0xFFE11D48)
            )
        }
    }
}

@Composable
private fun SelectionChip(text: String, selected: Boolean, accent: Color, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, if (selected) accent else Color.LightGray),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) accent.copy(alpha = 0.1f) else Color.Transparent
        ),
        modifier = Modifier.height(40.dp)
    ) {
        Text(
            text,
            color = if (selected) accent else Color.Gray
        )
    }
}

@Composable
private fun inputColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedIndicatorColor = Color(0xFF4F46E5),
    unfocusedIndicatorColor = Color(0xFFE5E7EB)
)
