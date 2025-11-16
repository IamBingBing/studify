package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun groupHome(vm: groupVM = hiltViewModel() , navController: NavController) {
    val groupName = vm.groupName.value
    val groupGoal = vm.groupGoal.value
    val hashTags = vm.hashTags.value
    Scaffold ( topBar = {
        groupNavigation(navController = navController) }, bottomBar = { navigationbar(navController) }){
        innerpadding->
        Column(modifier = BaseModifiers.BaseModifier.padding(innerpadding)) {

            sectionTitle("그룹정보")
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text("- 그룹이름: $groupName")
                Text("- 목표/다짐: $groupGoal")
                Text("- 목적: ${hashTags.joinToString(" ") { "#$it" }}")
            }

            Spacer(Modifier.height(12.dp))

            SectionDivider()

            sectionTitle("스터디 일정")
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text("11/15(토) 10:30 · 한경대 도서관 스터디룸 5")
                Text("참석 1/20", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(12.dp))

            SectionDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                sectionTitle("공지")
            }

            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text("[핀 고정] 이번 주 교재 3장까지")
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))
                Text("최신 공지: 지각 10분 이내 합류 가능")
            }
        }
    }
}

@Composable
private fun sectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 16.dp, top = 14.dp, bottom = 6.dp)
    )
}

@Composable
private fun SectionDivider() {
    HorizontalDivider(thickness = 8.dp, color = MaterialTheme.colorScheme.surfaceVariant)
}
