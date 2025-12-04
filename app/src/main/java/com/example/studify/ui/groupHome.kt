package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import androidx.compose.ui.graphics.Color


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun groupHome(
    vm: groupVM = hiltViewModel(),
    navController: NavController
) {
    val groupName by vm.groupName
    val groupGoal by vm.groupGoal
    val hashTags by vm.hashTags
    val errorMessage by vm.errorMessage
    val dates by vm.dates
    val announcements by vm.announce

    Scaffold(
        topBar = { groupNavigation(navController = navController) },
        bottomBar = { navigationbar(navController) }
    ) { innerPadding ->

        Column(
            modifier = BaseModifiers.BaseModifier.padding(innerPadding)
        ) {

            if (!errorMessage.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            sectionTitle("그룹 정보")
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("• 그룹이름: $groupName", style = MaterialTheme.typography.bodyMedium)
                Text("• 목표/다짐: $groupGoal", style = MaterialTheme.typography.bodyMedium)
                Text("• 목적: $hashTags", style = MaterialTheme.typography.bodyMedium)

            }

            Spacer(Modifier.height(12.dp))
            SectionDivider()

            sectionTitle("캘린더 일정")
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                if (dates.isEmpty()) {
                    Text("등록된 일정이 없습니다.")
                } else {
                    dates.forEach { item ->
                        val rawTime = item.time.orEmpty()
                        val formattedTime = when {
                            rawTime.length >= 16 -> rawTime.substring(5, 16)  // "11-23 09:00"
                            else -> rawTime
                        }

                        val locationText = item.location.ifBlank { "-" }
                        val titleText = item.title.ifBlank { "(제목 없음)" }

                        // 2) 최종 출력 포맷
                        val line = "• $titleText : $formattedTime | $locationText"

                        // 3) 출력
                        Text(
                            text = line,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(Modifier.height(6.dp))
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            SectionDivider()

            sectionTitle("공지")

            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                if (announcements.isEmpty()) {
                    Text("공지사항이 없습니다.")
                } else {
                    announcements.forEachIndexed { index, announcement ->
                        if (index > 0) {
                            //HorizontalDivider()
                            Spacer(Modifier.height(8.dp))
                        }
                        val title = announcement.announceName ?: "(제목 없음)"
                        val titleText = if (announcement.isPin == true) "• [필독] $title" else "• $title"

                        Text(
                            titleText,
                            style = MaterialTheme.typography.bodyMedium,
                            /*color = if (announcement.isPin == true)
                                MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurface*/
                        )
                    }
                }
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
    HorizontalDivider(thickness = 8.dp, color = Color(0xFFDFE5F3))
}
