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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun profilepage(vm : profilepageVM = hiltViewModel(), navController: NavController) {
    var userName by vm.userName
    var studyStyle by vm.studyStyle
    var mannerScore by vm.mannerScore
    var reviewTags = vm.reviewTags
    var studyHistory by vm.studyHistory

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
