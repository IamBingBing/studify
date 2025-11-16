package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studify.Tool.BaseModifiers
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@ExperimentalMaterial3Api
@Composable
fun group(vm: groupVM= viewModel(), navController: NavController) {
    var groupName by vm.groupName
    val selectedTab = mapOf(0 to "home", 1 to "calender",2 to "member",3 to "notice")
    var currentTab by vm.currentTab //화면이 리컴포즈될때마다 0으로 초기화돼서 탭이 자꾸 홈으로 이동하는 거 방지
    var groupGoal by vm.groupGoal
    var hashTags by vm.hashTags


    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(groupName) }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(selectedTabIndex = currentTab) {
                selectedTab.keys.forEach { tab ->
                    Tab(
                        selected = tab == currentTab,
                        onClick = {
                            if (tab == 3) {
                                navController.navigate("notice")
                            } else {
                                currentTab = tab
                            }
                        },
                        text = {
                            Text(
                                when (tab) {
                                    0 -> "홈"
                                    1 -> "캘린더"
                                    2-> "멤버"
                                    3 -> "공지"
                                    else -> ""
                                }
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            when (currentTab) {
                0 -> HomeTab(
                    groupName = groupName,
                    groupGoal = groupGoal,
                    hashTags = hashTags,
                    onNoteClick = { }
                )
                1 -> CalendarTab()
                2  -> MemberTab()
                3   -> NoticeTab()
            }
        }
    }
}

@Composable
fun HomeTab(
    groupName: String = "Ctrl + F",
    groupGoal: String = "잠은 죽어서 자자",
    hashTags: List<String> = listOf("프론트엔드개발", "알고리즘"),
    onNoteClick: () -> Unit
) {
    Column(modifier = BaseModifiers.BaseTextfillModifier) {


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

@Composable
private fun sectionTitle(title: String) { //각 구역마다 타이틀 보여줌
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

@Composable
private fun CalendarTab() {
    Box(

        modifier = BaseModifiers.BaseTextfillModifier.height(200.dp),

        contentAlignment = Alignment.Center
    ) { Text("캘린더 (추가 예정)") }
}

@Composable
private fun MemberTab() {
    Box(

        modifier = BaseModifiers.BaseTextfillModifier.height(200.dp),

        contentAlignment = Alignment.Center
    ) { Text("멤버 (추가 예정)") }
}

@Composable
private fun NoticeTab() {
    Box(

        modifier = BaseModifiers.BaseTextfillModifier.height(200.dp),

        contentAlignment = Alignment.Center
    ) { Text("공지") }
}
