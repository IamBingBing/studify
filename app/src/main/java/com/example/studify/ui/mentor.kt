package com.example.studify.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Brush

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

    // 멘토 ID 가져오기
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

@Composable
private fun MentorHomeTab(vm: mentorVM) {
    val iWillTeach by vm.iWillTeach
    val iWillLearn by vm.iWillLearn
    val groupName by vm.groupName
    val recentQna by vm.recentQna

    Column(
        modifier = BaseModifiers.BaseModifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // ─────── 멘토-멘티 정보 ─────────
        sectionTitle("지식 교환")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 🔹 카드 1: 내가 알려줄 내용
            ElevatedCard(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFF51669D)   // 진한 블루
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "내가 알려줄 것",
                        fontSize = 13.sp,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFE0E3F1)
                    )
                    Text(
                        text = iWillTeach.ifBlank { "-" },
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }

            // 🔹 카드 2: 내가 배울 내용
            ElevatedCard(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFF6F82BC)   // 조금 더 밝은 블루
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "내가 배울 것",
                        fontSize = 13.sp,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFE0E3F1)
                    )
                    Text(
                        text = iWillLearn.ifBlank { "-" },
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 섹션 2: 최근 Q&A
        sectionTitle("최근 Q&A")

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color(0xFFE1E7F5)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (recentQna.isEmpty()) {
                    Text(
                        text = "등록된 Q&A가 없습니다.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                } else {
                    recentQna.forEachIndexed { index, qna ->
                        val title = qna.qnatitle ?: "(제목 없음)"

                        Text(
                            text = "• $title",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        if (index != recentQna.lastIndex) {
                            Divider(
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = Color(0xFFB6BDE3)
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
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

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (mentorList.isEmpty()) {
                Text("등록된 멘토가 없습니다.", color = Color.Gray)
            } else {
                mentorList.forEach { mentor ->
                    MemberCard(
                        title = mentor.name,
                        subtitle = mentor.field
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 섹션 2: 멘티
        sectionTitle("멘티 (Mentee)")

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (menteeList.isEmpty()) {
                Text("등록된 멘티가 없습니다.", color = Color.Gray)
            } else {
                menteeList.forEach { mentee ->
                    MemberCard(
                        title = mentee.name,
                        subtitle = mentee.goal
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}


// --- 스타일 컴포넌트 ---

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
        color = Color(0xFFDFE5F3)
    )
}

@Composable
private fun MemberCard(
    title: String,
    subtitle: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = Color(0xFFE0E8F5),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}