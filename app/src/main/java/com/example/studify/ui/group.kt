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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@ExperimentalMaterial3Api
@Composable
fun group(vm: groupVM= hiltViewModel(),
          navController: NavController) {
    var groupName by vm.groupName
    val selectedTab = mapOf(0 to "home", 1 to "calender",2 to "member",3 to "notice", 4 to "progress")
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
                                    4 -> "진도체크"
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
                4    -> progressTab()
            }
        }
    }
}

@Composable
fun HomeTab(
    groupName: String,
    groupGoal: String,
    hashTags: List<String>,
    onNoteClick: () -> Unit
) {
    Box(
        modifier = BaseModifiers.BaseTextfillModifier.height(200.dp),

        contentAlignment = Alignment.Center
    ) { Text("홈") }
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

@Composable
private fun progressTab() {
    Box(

        modifier = BaseModifiers.BaseTextfillModifier.height(200.dp),

        contentAlignment = Alignment.Center
    ) { Text("진도체크") }
}