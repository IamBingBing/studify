package com.example.studify.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify.Tool.BaseModifiers

@Composable
@Preview
fun MatchingIng() {
    Column(
        modifier = BaseModifiers.BaseBoxModifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            color = Color(0xFF4A80F5), // 파란색
            strokeWidth = 8.dp
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "매칭 중입니다.",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "잠시만 기다려주시기 바랍니다.",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}