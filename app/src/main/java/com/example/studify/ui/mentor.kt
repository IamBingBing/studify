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
import androidx.compose.ui.unit.dp
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
    val currentGroupId = 1

    val tabs = listOf("홈", "멤버", "Q&A")

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
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = currentTab == index,
                        onClick = {
                            when (index) {
                                0 -> currentTab = 0
                                1 -> currentTab = 1
                                2 -> { navController.navigate("qna/$currentGroupId") }
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            when (currentTab) {
                0 -> MentorHomeTab(vm = vm)
                1 -> MentorMemberTab(vm = vm)
            }
        }
    }
}

@Composable
private fun MentorHomeTab(vm: mentorVM) {
    val mentorCanTeach by vm.mentorCanTeach
    val menteeWants by vm.menteeWants

    Column(
        modifier = BaseModifiers.BaseBoxModifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        MentorSectionTitle("그룹 정보")

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(label = "멘토가 가능한 것", value = mentorCanTeach)
                InfoRow(label = "멘티가 배우고 싶은 것", value = menteeWants)
            }
        }

        Spacer(Modifier.height(16.dp))
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
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(130.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}