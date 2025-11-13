package com.example.studify.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
@Preview
fun profilepage(vm : profilepageVM = viewModel() , navController: NavController) {
    val userName = "이종원"
    val studyStyle = "아침형인간"
    val mannerScore = 98.5
    val reviewTags = listOf("시간 약속을 잘 지켜요", "설명을 잘해줘요", "적극적이에요")
    val studyHistory = "알고리즘 스터디, 토익 스터디"

    Column(
        modifier = BaseModifiers.BaseBoxModifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "상세 프로필",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ProfileInfoRow(label = "이름", value = userName)
        ProfileInfoRow(label = "학습 스타일", value = studyStyle)
        ProfileInfoRow(label = "매너 점수", value = "$mannerScore 점")
        ProfileInfoRow(label = "스터디 내역", value = studyHistory)
        ProfileInfoRow(label = "칭찬 리뷰", value = reviewTags.joinToString(", "))

    }
}

@Composable
public fun ProfileInfoRow(label: String, value: String) {
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
