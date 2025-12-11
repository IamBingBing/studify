package com.example.studify.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profilepage(vm: profilepageVM = hiltViewModel(), navController: NavController) {

    val name by vm.name
    val email by vm.email
    val context = LocalContext.current
    var isReportDialogOpen by remember { mutableStateOf(false) }
    var reportText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("상세 프로필") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = BaseModifiers.BaseBoxModifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // 위쪽 정보 영역
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileInfoRow(label = "이름", value = name)
                ProfileInfoRow(label = "이메일", value = email)

                Spacer(modifier = Modifier.height(80.dp))
            }

            Button(
                onClick = { isReportDialogOpen = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("신고")
            }

            if (isReportDialogOpen) {
                AlertDialog(
                    onDismissRequest = {
                        isReportDialogOpen = false
                    },
                    title = { Text(text = "신고하기") },
                    text = {
                        Column {
                            Text(text = "신고 내용을 작성해 주세요.")
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = reportText,
                                onValueChange = { reportText = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("신고 사유를 입력하세요.") },
                                minLines = 3
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "신고가 접수되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                reportText = ""
                                isReportDialogOpen = false
                            }
                        ) {
                            Text("보내기")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                reportText = ""
                                isReportDialogOpen = false
                            }
                        ) {
                            Text("취소")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
    }
}