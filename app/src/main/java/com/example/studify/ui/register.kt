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
    // [1] VM 상태 변수들을 UI 변수로 위임 (by)
    // 이렇게 하면 UI에서는 .value 없이 일반 변수처럼 쓸 수 있어 깔끔합니다.
    var email by vm.email
    var authCode by vm.authcode
    var sex by vm.sex
    var adress by vm.adress
    var username by vm.username
    var userid by vm.userid
    var pw by vm.pw
    var repw by vm.repw

    // 관찰 가능한 상태들 (Flow, State)
    val isSuccess by vm.registerSuccess.collectAsState()
    val errorMsg by vm.registerError.collectAsState()

    // VM의 MutableState도 by로 가져오면 UI 갱신이 자동 처리됩니다.
    val isCodeSent by vm.isCodeSent
    val isEmailVerified by vm.isEmailVerified

    // [2] UI 로컬 상태
    var isTermsAgreed by remember { mutableStateOf(false) } // 약관 동의 체크
    var showTermsSheet by remember { mutableStateOf(false) } // 약관 시트 표시
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
                                // [중요] 인증번호가 발송된 상태(성공)라면 수정 금지
                                // 중복 에러 등으로 실패했다면 isCodeSent는 false이므로 계속 수정 가능
                                enabled = !isCodeSent,
                                singleLine = true,
                                placeholder = { Text("예: abc@hknu.ac.kr") },
                                colors = inputColors()
                            )

                            OutlinedButton(
                                onClick = {
                                    // 빈 값이 아닐 때만 요청
                                    if (email.isNotEmpty()) {
                                        vm.requestEmailCode() // 인자 없이 호출 (VM이 email.value 사용)
                                        keyboardController?.hide()
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, accent),
                            ) {
                                Text(
                                    // 발송 성공시 '재전송', 아직 안 보냈거나 실패시 '인증요청'
                                    text = if (isCodeSent) "재전송" else "인증요청",
                                    fontSize = 13.sp,
                                    color = accent,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // ------- 인증번호 입력칸 (발송 성공시에만 보임) -------
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
                                        vm.verifyEmailCode() // 인자 없이 호출
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
                            // 인증 완료 메시지
                            if (isEmailVerified) {
                                Text(
                                    text = "인증되었습니다.",
                                    color = Color(0xFF16A34A), // Green
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                                )
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        // ------- 나머지 입력 필드들 -------
                        ModernFieldLabel("아이디", true)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            value = userid,
                            onValueChange = { userid = it },
                            singleLine = true,
                            colors = inputColors()
                        )

                        ModernFieldLabel("비밀번호", true)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            value = pw,
                            onValueChange = { pw = it },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = inputColors()
                        )

                        ModernFieldLabel("비밀번호 확인", true)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            value = repw,
                            onValueChange = { repw = it },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = inputColors()
                        )

                        ModernFieldLabel("이름", true)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            value = username,
                            onValueChange = { username = it },
                            singleLine = true,
                            colors = inputColors()
                        )

                        ModernFieldLabel("주소", false)
                        TextField(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            value = adress, // VM의 변수명(adress)과 일치
                            onValueChange = { adress = it },
                            singleLine = true,
                            colors = inputColors()
                        )

                        ModernFieldLabel("성별", true)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SelectionChip("남자", sex == 0, accent) { vm.onSexSelected(0) }
                            SelectionChip("여자", sex == 1, accent) { vm.onSexSelected(1) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // [에러 메시지 표시]
                // 인증이 아직 안 됐는데 에러 메시지가 있다면 보여줌 (중복 이메일 등)
                if (errorMsg != null && !isEmailVerified) {
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
                        // 약관 동의 여부에 따라 색상 변경
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

                // ===== 이용약관 체크박스 =====
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTermsSheet = true },
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

                // [네비게이션] 회원가입 성공 시 로그인 화면으로 이동
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
                               Studify 서비스 이용약관
제1조 (목적)
본 약관은 Studify(이하 “회사”라 합니다)가 제공하는 Studify 서비스(이하 “서비스”라 합니다)의 이용과 관련하여 회사와 이용자 간의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.

제2조 (용어의 정의)
본 약관에서 사용하는 용어의 정의는 다음과 같습니다.
1. “서비스”란 회사가 제공하는 스터디 매칭, 질의응답(Q&A), 채팅, 콘텐츠 제공 등 Studify와 관련된 모든 온라인 서비스를 의미합니다.
2. “이용자”란 본 약관에 따라 회사가 제공하는 서비스를 이용하는 회원 및 비회원을 말합니다.
3. “회원”이란 서비스에 회원가입을 하고 본 약관에 동의하여 서비스를 지속적으로 이용하는 자를 말합니다.
4. “아이디(ID)”란 회원 식별과 서비스 이용을 위하여 회원이 정하고 회사가 승인한 이메일 주소를 말합니다.
5. “비밀번호”란 회원의 개인정보 보호 및 본인 확인을 위해 회원이 설정한 문자와 숫자의 조합을 말합니다.

제2조 (용어의 정의)
본 약관에서 사용하는 용어의 정의는 다음과 같습니다.
1. “서비스”란 회사가 제공하는 스터디 매칭, 질의응답(Q&A), 채팅, 콘텐츠 제공 등 Studify와 관련된 모든 온라인 서비스를 의미합니다.
2. “이용자”란 본 약관에 따라 회사가 제공하는 서비스를 이용하는 회원 및 비회원을 말합니다.
3. “회원”이란 서비스에 회원가입을 하고 본 약관에 동의하여 서비스를 지속적으로 이용하는 자를 말합니다.
4. “아이디(ID)”란 회원 식별과 서비스 이용을 위하여 회원이 정하고 회사가 승인한 이메일 주소를 말합니다.
5. “비밀번호”란 회원의 개인정보 보호 및 본인 확인을 위해 회원이 설정한 문자와 숫자의 조합을 말합니다.
제3조 (약관의 효력 및 변경)
1. 본 약관은 서비스 화면에 게시하거나 기타의 방법으로 이용자에게 공지함으로써 효력이 발생합니다.
2. 회사는 관련 법령을 위반하지 않는 범위 내에서 본 약관을 개정할 수 있습니다.
회사가 약관을 개정할 경우에는 적용일자 및 개정 사유를 명시하여 서비스 화면에 사전 공지합니다.
3. 이용자가 개정 약관에 동의하지 않을 경우 서비스 이용을 중단하고 회원 탈퇴를 할 수 있습니다.
제4조 (회원가입)
1. 회원가입은 이용자가 본 약관에 동의하고 회사가 정한 가입 양식에 따라 정보를 입력한 후 가입 신청을 함으로써 이루어집니다.
2. 회사는 다음 각 호에 해당하는 경우 회원가입을 승인하지 않을 수 있습니다.
     1.허위 정보를 기재한 경우
     2. 타인의 정보를 도용한 경우
     3. 기타 회사가 정한 가입 요건을 충족하지 못한 경우
                            """.trimIndent(),
                            fontSize = 14.sp,
                            color = Color(0xFF4B5563),
                            lineHeight = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            isTermsAgreed = true
                            showTermsSheet = false
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
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
            Text(" *", fontSize = 13.sp, color = Color(0xFFE11D48))
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
        Text(text, color = if (selected) accent else Color.Gray)
    }
}

@Composable
private fun inputColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color(0xFFF3F4F6), // 비활성화 배경색
    focusedIndicatorColor = Color(0xFF4F46E5),
    unfocusedIndicatorColor = Color(0xFFE5E7EB),
    disabledIndicatorColor = Color.Transparent, // 비활성화 시 밑줄 제거
    disabledTextColor = Color.Gray            // 비활성화 시 텍스트 색
)