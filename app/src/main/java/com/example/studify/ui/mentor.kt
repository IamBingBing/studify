package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
fun mentor(vm : mentorVM = hiltViewModel() , navController: NavController) {
    var groupName by vm.groupName
    var currentTab by vm.currentTab

    val selectedTab = mapOf(
        0 to "home",
        1 to "calendar",
        2 to "member",
        3 to "notice",
        4 to "progress"
    )

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
                            when (tab) {
                                3 -> navController.navigate("notice")
                                4 -> navController.navigate("progress")
                                else -> currentTab = tab
                            }
                        },
                        text = {
                            Text(
                                when (tab) {
                                    0 -> "홈"
                                    1 -> "캘린더"
                                    2 -> "멤버"
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
                0 -> MentorHomeTab(vm = vm)
                1 -> MentorCalendarTab()
                2 -> MentorMemberTab(vm = vm)
            }
        }
    }
}

@Composable
private fun MentorHomeTab(vm: mentorVM) {
    val mentorCanTeach by vm.mentorCanTeach
    val menteeWants by vm.menteeWants
    val studySchedules = vm.studySchedules
    val notices = vm.notices
    val groupName by vm.groupName

    Column(
        modifier = BaseModifiers.BaseBoxModifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        MentorSectionTitle("그룹 정보")

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(label = "그룹 이름", value = groupName)
                InfoRow(label = "멘토가 가능한 것", value = mentorCanTeach)
                InfoRow(label = "멘티가 배우고 싶은 것", value = menteeWants)
            }
        }

        MentorSectionDivider()

        MentorSectionTitle("스터디 일정")

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (studySchedules.isEmpty()) {
                    Text("등록된 일정이 없습니다.")
                } else {
                    studySchedules.forEach { line ->
                        Text("• $line", modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }

        MentorSectionDivider()

        MentorSectionTitle("공지")

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (notices.isEmpty()) {
                    Text("등록된 공지가 없습니다.")
                } else {
                    notices.forEachIndexed { index, notice ->
                        Text("${index + 1}. $notice", modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun MentorCalendarTab() {
    Box(
        modifier = BaseModifiers.BaseTextfillModifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("캘린더 화면 (구현 예정)")
    }
}

@Composable
private fun MentorMemberTab(vm: mentorVM) {
    val mentorList = vm.mentorList
    val menteeList = vm.menteeList

    Column(
        modifier = BaseModifiers.BaseBoxModifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        MentorSectionTitle("멘토")

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (mentorList.isEmpty()) {
                    Text("등록된 멘토가 없습니다.")
                } else {
                    mentorList.forEach { mentor ->
                        Text("- ${mentor.name} (${mentor.field})", modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }

        MentorSectionDivider()

        MentorSectionTitle("멘티")

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (menteeList.isEmpty()) {
                    Text("등록된 멘티가 없습니다.")
                } else {
                    menteeList.forEach { mentee ->
                        Text("- ${mentee.name} (${mentee.goal})", modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun MentorSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 4.dp, top = 14.dp, bottom = 6.dp)
    )
}

@Composable
private fun MentorSectionDivider() {
    HorizontalDivider(
        thickness = 8.dp,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(110.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
