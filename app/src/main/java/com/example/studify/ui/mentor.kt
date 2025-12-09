package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify.Tool.BaseModifiers

data class MentorInfo(
    val name: String,
    val field: String
)

data class MenteeInfo(
    val name: String,
    val goal: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mentor(vm: mentorVM = hiltViewModel(), navController: NavController) {
    var groupName by vm.groupName
    var currentTab by vm.currentTab

    //  멘토 ID 가져오기
    val currentMentorId = vm.currentMentorId.value

    val tabs = listOf("홈", "멤버", "Q&A")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(groupName, fontWeight = FontWeight.Bold) }
            )
        },
        bottomBar = { navigationbar(navController = navController) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 탭 바
            TabRow(
                selectedTabIndex = currentTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = currentTab == index,
                        onClick = {
                            when (index) {
                                0 -> currentTab = 0  // 홈
                                1 -> currentTab = 1  // 멤버
                                2 -> {
                                    navController.navigate("mentorQna/$currentMentorId")
                                }
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }

            // 탭 내용 표시
            when (currentTab) {
                0 -> MentorHomeTab(vm = vm)    // 홈
                1 -> MentorMemberTab(vm = vm)  // 멤버
            }
        }
    }
}

// [홈 탭] groupHome과 동일한 텍스트 나열 방식
@Composable
private fun MentorHomeTab(vm: mentorVM) {
    val mentorCanTeach by vm.mentorCanTeach
    val menteeWants by vm.menteeWants
    val groupName by vm.groupName
    val recentQna by vm.recentQna

    Column(
        modifier = BaseModifiers.BaseModifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 섹션 1: 그룹 정보
        sectionTitle("그룹 정보")
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("• 그룹이름: $groupName", style = MaterialTheme.typography.bodyMedium)
            Text("• 멘토 과목: $mentorCanTeach", style = MaterialTheme.typography.bodyMedium)

            // 멘티 목표 라벨 처리
            val label = if (menteeWants.contains("배움") || menteeWants.contains("학습")) "멘티 목표" else "멘티 과목"
            Text("• $label: $menteeWants", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(12.dp))
        SectionDivider() // 굵은 구분선

        //  섹션 2: 최근 Q&A
        sectionTitle("최근 Q&A")
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            if (recentQna.isEmpty()) {
                Text("등록된 Q&A가 없습니다.")
            } else {
                recentQna.forEachIndexed { index, qna ->
                    if (index > 0) {
                        Spacer(Modifier.height(8.dp))
                    }
                    val title = qna.qnatitle ?: "(제목 없음)"
                    Text(
                        "• $title",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        SectionDivider()
    }
}

// [멤버 탭]
@Composable
private fun MentorMemberTab(vm: mentorVM) {
    val mentorList = vm.mentorList
    val menteeList = vm.menteeList

    Column(
        modifier = BaseModifiers.BaseModifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 섹션 1: 멘토
        sectionTitle("멘토 (Mentor)")
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            if (mentorList.isEmpty()) {
                Text("등록된 멘토가 없습니다.", color = Color.Gray)
            } else {
                mentorList.forEach { mentor ->
                    Text(
                        text = "• ${mentor.name} (${mentor.field})",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        SectionDivider()

        // 섹션 2: 멘티
        sectionTitle("멘티 (Mentee)")
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            if (menteeList.isEmpty()) {
                Text("등록된 멘티가 없습니다.", color = Color.Gray)
            } else {
                menteeList.forEach { mentee ->
                    Text(
                        text = "• ${mentee.name} (${mentee.goal})",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        SectionDivider()
    }
}

// --- groupHome.kt 스타일 컴포넌트 (그대로 가져옴) ---

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
    HorizontalDivider(
        thickness = 8.dp,
        color = Color(0xFFDFE5F3) // groupHome 색상
    )
}