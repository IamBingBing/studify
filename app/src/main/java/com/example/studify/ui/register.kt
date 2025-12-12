@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.studify.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun register(
    vm: registerVM = hiltViewModel(),
    navController: NavController
) {
    // ViewModel 상태
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

    // UI 로컬 상태
    var isTermsAgreed by remember { mutableStateOf(false) } // 약관 동의 체크 여부
    var showTermsSheet by remember { mutableStateOf(false) } // 바텀 시트 표시 여부
    val sheetState = rememberModalBottomSheetState()

    val context = LocalContext.current
    val scrollState = rememberScrollState()
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
                    .verticalScroll(scrollState),
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
                    textAlign = TextAlign.Center
                )

                // ===== 입력 폼 카드 =====
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
                                // [수정] 인증 코드가 발송되면 수정 불가
                                enabled = !isCodeSent,
                                singleLine = true,
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
                                    color = Color(0xFF16A34A),
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

                // 에러 메시지
                if (errorMsg != null && !vm.isEmailVerified.value) {
                    Text(
                        text = errorMsg!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }

                // ===== 가입하기 버튼 =====
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        // 동의 안 하면 회색, 동의 하면 포인트 컬러
                        containerColor = if (isTermsAgreed) accent else Color.Gray
                    ),
                    onClick = {
                        if (!isTermsAgreed) {
                            Toast.makeText(context, "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
                        } else {
                            vm.register()
                        }
                    }
                ) {
                    Text(
                        "가입하기",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ===== 이용약관 체크박스 & 텍스트 =====
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTermsSheet = true }, // 클릭 시 시트 오픈
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(
                        checked = isTermsAgreed,
                        onCheckedChange = { isTermsAgreed = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = accent,
                            uncheckedColor = Color.Gray
                        )
                    )
                    Text(
                        text = "이용약관 확인 및 동의 (필수)",
                        fontSize = 14.sp,
                        color = Color(0xFF374151),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 회원가입 성공 시 화면 이동
                LaunchedEffect(isSuccess) {
                    if (isSuccess) navController.navigate("login")
                }
            }
        }

        // ===== 이용약관 바텀 시트 =====
        if (showTermsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showTermsSheet = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 32.dp)
                ) {
                    Text(
                        text = "서비스 이용약관",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // 약관 내용 스크롤 영역
                    Column(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .background(Color(0xFFF3F4F6), RoundedCornerShape(8.dp))
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = """
                                제1조 (목적)
                                본 약관은 Studify 서비스의 이용과 관련하여 회사와 회원 간의 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.
                                
                                제2조 (개인정보 수집)
                                서비스 제공을 위해 필요한 최소한의 개인정보를 수집합니다. 수집된 정보는 회원 탈퇴 시 파기됩니다.
                                
                                제3조 (회원의 의무)
                                회원은 타인의 정보를 도용하거나 서비스를 부정하게 이용해서는 안 됩니다.
                                
                                (이하 생략...)
                                본 서비스는 학생들의 편의를 위해 제공되며...
                            """.trimIndent(),
                            fontSize = 14.sp,
                            color = Color(0xFF4B5563),
                            lineHeight = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 시트 내부 동의 버튼
                    Button(
                        onClick = {
                            isTermsAgreed = true
                            showTermsSheet = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accent),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("동의하고 닫기", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// --- Helper UI Components ---

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
    disabledContainerColor = Color(0xFFF3F4F6), // [수정] 비활성화 시 배경색 (연회색)
    focusedIndicatorColor = Color(0xFF4F46E5),
    unfocusedIndicatorColor = Color(0xFFE5E7EB),
    disabledIndicatorColor = Color.Transparent, // [수정] 비활성화 시 밑줄 없음
    disabledTextColor = Color.Gray            // [수정] 비활성화 시 텍스트 색상
)