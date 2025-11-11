package com.example.studify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studify.Tool.BaseModifiers
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api

enum class GroupTab { HOME, CALENDAR, MEMBERS, NOTICE }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun groupPage(groupName: String = "Ctrl+F") {
    var selectedTab by remember { mutableStateOf(GroupTab.HOME) }

    Scaffold( //화면의 기본구조를 잡아주는 뼈대, 위 아래 본문 이런것들을 알아서 배치해줌
        topBar = { CenterAlignedTopAppBar(title = { Text(groupName) }) }
    ) { inner -> //위에 topbar(맨위 그룹이름)있으니까 여기서부터 본문 시작. inner라는 이름으로 스캐폴드가 넘겨준 패딩값을 받아온다
        Column(
            modifier = Modifier
                .padding(inner) //스캐폴드가 넘겨준 패딩값, 탑바 높이 만큼 살짝 내려줌
                .fillMaxSize(), //남은 화면 세로 가로로 꽉 채움
            horizontalAlignment = Alignment.CenterHorizontally //가로기준 가운데 정렬
        ) {
            TabRow(selectedTabIndex = selectedTab.ordinal) { //가로로 탭버튼 올려놓는 줄
                GroupTab.values().forEach { tab ->
                    Tab(
                        selected = tab == selectedTab,
                        onClick = { selectedTab = tab },
                        text = {
                            Text(
                                when (tab) {
                                    GroupTab.HOME -> "홈"
                                    GroupTab.CALENDAR -> "캘린더"
                                    GroupTab.MEMBERS -> "멤버"
                                    GroupTab.NOTICE -> "공지"
                                }
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            when (selectedTab) {
                GroupTab.HOME -> HomeTab(
                    onNoteClick = { selectedTab = GroupTab.NOTICE }
                )
                GroupTab.CALENDAR -> CalendarTab()
                GroupTab.MEMBERS  -> MembersTab()
                GroupTab.NOTICE   -> noticePage()
            }
        }
    }
}

@Composable
fun HomeTab(
    groupName: String = "Ctrl + F",
    groupGoal: String = "잠은 죽어서 자자",
    tags: List<String> = listOf("프론트엔드개발", "알고리즘"),
    onNoteClick: () -> Unit
) {
    Column(modifier = BaseModifiers.BaseTextfill) {


        SectionTitle("그룹정보")
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text("- 그룹이름: $groupName")
            Text("- 목표/다짐: $groupGoal")
            Text("- 목적: ${tags.joinToString(" ") { "#$it" }}")
        }

        Spacer(Modifier.height(12.dp))

        SectionDivider()

        SectionTitle("스터디 일정")
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
            SectionTitle("공지")


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
private fun SectionTitle(title: String) { //각 구역마다 타이틀 보여줌
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

        modifier = BaseModifiers.BaseTextfill.height(200.dp),

        contentAlignment = Alignment.Center
    ) { Text("캘린더 (추가 예정)") }
}

@Composable
private fun MembersTab() {
    Box(

        modifier = BaseModifiers.BaseTextfill.height(200.dp),

        contentAlignment = Alignment.Center
    ) { Text("멤버 (추가 예정)") }
}

@Composable
private fun NoticeTab() {
    Box(

        modifier = BaseModifiers.BaseTextfill.height(200.dp),

        contentAlignment = Alignment.Center
    ) { Text("공지 (추가 예정)") }
}
