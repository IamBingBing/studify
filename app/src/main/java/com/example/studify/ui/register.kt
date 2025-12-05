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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import com.example.studify.Tool.BaseModifiers

@Composable
fun register(
    vm: registerVM = hiltViewModel(),
    navController: NavController
) {
    // 변수 연결
    var email by vm.email
    var authCode by vm.authcode
    var sex by vm.sex
    var adress by vm.adress
    var username by vm.username
    var userid by vm.userid
    var pw by vm.pw
    var repw by vm.repw

    val isSuccess by vm.registerSuccess.collectAsState()
    var isCodeSent by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val accent = Color(0xFF4F46E5) // 파란색 포인트 컬러

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF0F172A), Color(0xFF020617))
                    )
                )
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // [헤더]
                Text(
                    text = "회원가입",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    textAlign = TextAlign.Start
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        // --- [1. 이메일 & 인증번호 요청] ---
                        ModernFieldLabel("학교 이메일", required = true)

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically // 높이 중앙 정렬
                        ) {
                            TextField(
                                modifier = Modifier
                                    .weight(1f) // [핵심] 입력창이 남은 공간 다 차지
                                    .padding(end = 8.dp),
                                value = email,
                                onValueChange = { email = it },
                                singleLine = true,
                                placeholder = { Text("example@hknu.ac.kr") },
                                colors = inputColors()
                            )

                            // [핵심] 디자인 예시처럼 테두리 버튼 사용
                            OutlinedButton(
                                onClick = {
                                    if (email.isNotEmpty()) {
                                        vm.requestEmailCode(email)
                                        isCodeSent = true
                                        keyboardController?.hide()
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, accent),
                                // 버튼 크기 고정 (디자인 통일감)
                                modifier = Modifier.height(56.dp)
                            ) {
                                Text(
                                    text = if (isCodeSent) "재전송" else "인증요청",
                                    fontSize = 14.sp,
                                    color = accent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // --- [2. 인증번호 입력 & 확인] ---
                        if (isCodeSent) {
                            ModernFieldLabel("인증번호", required = true)
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
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
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = inputColors()
                                )
                                OutlinedButton(
                                    onClick = {
                                        vm.verifyEmailCode(email, authCode)
                                        keyboardController?.hide()
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, accent),
                                    modifier = Modifier.height(56.dp)
                                ) {
                                    Text(
                                        text = "확인",
                                        fontSize = 14.sp,
                                        color = accent,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 12.dp))

                        // --- [나머지 입력 필드들] ---
                        ModernFieldLabel("아이디", true)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            value = userid, onValueChange = { userid = it }, singleLine = true, colors = inputColors()
                        )

                        ModernFieldLabel("비밀번호", true)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            value = pw, onValueChange = { pw = it }, singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = inputColors()
                        )

                        ModernFieldLabel("비밀번호 확인", true)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            value = repw, onValueChange = { repw = it }, singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = inputColors()
                        )

                        ModernFieldLabel("이름", true)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            value = username, onValueChange = { username = it }, singleLine = true, colors = inputColors()
                        )

                        ModernFieldLabel("주소", false)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            value = adress, onValueChange = { adress = it }, singleLine = true, colors = inputColors()
                        )

                        // 성별 선택
                        ModernFieldLabel("성별", true)
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            SelectionChip("남자", sex == 0, accent) { vm.onSexSelected(0) }
                            SelectionChip("여자", sex == 1, accent) { vm.onSexSelected(1) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // [가입하기 버튼]
                Button(
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accent),
                    onClick = { vm.register() }
                ) {
                    Text("가입하기", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(30.dp))

                LaunchedEffect(isSuccess) {
                    if (isSuccess) navController.navigate("grouplist")
                }
            }
        }
    }
}

// --- 보조 UI 요소들 ---
@Composable
private fun ModernFieldLabel(text: String, required: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {
        Text(text, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF111827))
        if (required) Text(" *", fontSize = 13.sp, color = Color(0xFFE11D48))
    }
}

@Composable
private fun SelectionChip(text: String, selected: Boolean, accent: Color, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, if (selected) accent else Color.LightGray),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = if (selected) accent.copy(alpha = 0.1f) else Color.Transparent),
        modifier = Modifier.height(40.dp)
    ) {
        Text(text, color = if (selected) accent else Color.Gray)
    }
}

@Composable
private fun inputColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
    focusedIndicatorColor = Color(0xFF4F46E5), unfocusedIndicatorColor = Color(0xFFE5E7EB)
)