package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalDate


// ───────────────── 데이터 표현용 UI 모델 ─────────────────
data class StudyScheduleUi(
    val title: String,
    val time: String,
    val location: String
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
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
        bottomBar = { navigationbar(navController = navController) }
    ) { innerPadding ->

        Column(
            modifier = BaseModifiers.BaseModifier
                .padding(innerPadding)
                .background(Color(0xFFF6F7FB))
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

            val today = LocalDate.now()
            val thisMonth = today.monthValue
            val thisYear = today.year

// ✅ 이번 달 전체 일정
            val monthDates = dates.filter {
                val date = LocalDate.parse(it.time.substring(0, 10))
                date.monthValue == thisMonth && date.year == thisYear
            }

// ✅ 이번 달 중 완료된 일정
            val doneCount = monthDates.count {
                val date = LocalDate.parse(it.time.substring(0, 10))
                date.isBefore(today)
            }

            val progress: Float =
                if (monthDates.isEmpty()) 0f
                else doneCount.toFloat() / monthDates.size.toFloat()



            // ================== 그룹 목표 ==================
            sectionTitle("그룹 목표")

            GroupInfoCard(
                purpose = hashTags,
                goal = groupGoal,
                progress = progress
            )

            Spacer(Modifier.height(9.dp))

            // ================== 스터디 일정 ==================
            sectionTitle("스터디 일정")



            val schedules: List<StudyScheduleUi> =
                dates.filter {
                    val date = LocalDate.parse(it.time.substring(0, 10))
                    !date.isBefore(today)
                }
                    .sortedBy { it.time }
                    .take(3)
                    .map { item ->
                        val rawTime = item.time.orEmpty()
                        val formattedTime =
                            if (rawTime.length >= 16) rawTime.substring(5, 16) else rawTime

                        StudyScheduleUi(
                            title = item.title.ifBlank { "(제목 없음)" },
                            time = formattedTime,
                            location = item.location.ifBlank { "-" }
                        )
                    }

                    .sortedBy { it.time }   // 시간순 정렬 (원하면 유지)
            // .take(3)             // 필요하면 최대 3개로 제한

            println("DEBUG_schedules_size = ${schedules.size}")

            CalendarSection(schedules)

            Spacer(Modifier.height(9.dp))

            // ================== 공지사항 ==================
            sectionTitle("공지사항")

            if (announcements.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text("공지사항이 없습니다.")
                }
            } else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(announcements) { announcement ->

                        val title = announcement.announceName ?: "(제목 없음)"
                        val isPin = announcement.isPin == true

                        Card(
                            modifier = Modifier
                                .width(130.dp)
                                .height(90.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isPin)
                                    Color(0xFFEFF3FF)
                                else
                                    Color(0xFFEFF3FF)   // 일반: 연한 파랑
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            onClick = {
                                // 🔗 공지 상세로 이동
                                announcement.announceId?.let { id ->
                                    navController.navigate("noticeDetail/$id")
                                }
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (isPin) "📌 필독" else "공지",
                                    fontSize = 12.sp,
                                    color = if (isPin) Color(0xFF856404) else Color(0xFF3B51C7)
                                )

                                Text(
                                    text = title,
                                    fontSize = 14.sp,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }   // Column
    }       // Scaffold
}

// ───────────────── 컴포저블들 ─────────────────

@Composable
private fun GroupInfoCard(
    purpose: String?,
    goal: String?,
    progress: Float
) {
    val purposeText = if (purpose.isNullOrBlank()) "스터디" else "$purpose 스터디"
    val goalText = if (goal.isNullOrBlank()) "아직 목표/다짐이 설정되지 않았어요." else goal

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF5271C9), Color(0xFFBEBCAB))
                )
            )
            .padding(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(text = purposeText, color = Color.White, fontSize = 18.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(text = goalText, color = Color.White, fontSize = 14.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun CalendarSection(schedules: List<StudyScheduleUi>) {
    if (schedules.isEmpty()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text("등록된 일정이 없습니다.")
                }
            }
        }
    } else {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(schedules) { item ->
                StudyScheduleCard(item)
            }
        }
    }
}

@Composable
private fun StudyScheduleCard(item: StudyScheduleUi) {
    Card(
        modifier = Modifier
            .width(130.dp)   // 한 화면에 3개 정도 보이도록
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B3B69)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )

            Column {
                Text(text = "${item.title}:", color = Color.White, fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))
                Text(text = item.time, color = Color.White, fontSize = 12.sp)
                Text(text = item.location, color = Color.White, fontSize = 12.sp)
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
