package com.example.studify.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

data class MemberProgress(
    val name: String,
    var progress: Float // 0.0f ~ 1.0f
)

@Composable
fun progress(navController: NavController) { // 함수 이름과 파라미터를 수정했습니다.
    var mainGoal by remember { mutableStateOf("") }
    var personalGoal by remember { mutableStateOf("") }
    val context = LocalContext.current

    // 그룹 멤버들의 데이터를 리스트로 관리 (remember를 통해 상태 유지)
    // 리스트 자체가 아니라 리스트 안의 '요소'가 바뀔 것이므로 mutableStateListOf 사용
    val memberData = remember {
        mutableStateListOf(
            MemberProgress("나", 0.1f),
            MemberProgress("멤버1", 0.45f),
            MemberProgress("멤버2", 0.7f),
            MemberProgress("멤버3", 0.8f),
            MemberProgress("멤버4", 0.55f)
        )
    }

    // 내 진행률을 조절하기 위한 슬라이더의 현재 값
    var myProgressSliderValue by remember { mutableStateOf(memberData.first { it.name == "나" }.progress * 100) }

    Column(
        modifier = BaseModifiers.BaseBoxModifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        GoalCard(title = "주요 목표", content = mainGoal, onContentChange = { mainGoal = it })
        Spacer(modifier = Modifier.height(16.dp))

        GoalCard(title = "개인 목표", content = personalGoal, onContentChange = { personalGoal = it })
        Spacer(modifier = Modifier.height(16.dp))

        // 멤버 데이터를 막대그래프 카드에 전달
        ProgressBarChartCard(members = memberData)

        Spacer(modifier = Modifier.height(24.dp))

        // 내 진행률을 조절하는 슬라이더
        Text("내 진행률 설정: ${myProgressSliderValue.toInt()}%", fontWeight = FontWeight.Bold)
        Slider(
            value = myProgressSliderValue,
            onValueChange = { myProgressSliderValue = it },
            valueRange = 0f..100f,
            steps = 99 // 1% 단위로 조절
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val myData = memberData.find { it.name == "나" }
                myData?.let {
                    val index = memberData.indexOf(it)
                    memberData[index] = it.copy(progress = myProgressSliderValue / 100f)
                }
                Toast.makeText(context, "공부 기록이 저장되었습니다!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("오늘 공부 인증")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
public fun GoalCard(title: String, content: String, onContentChange: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

/** 막대그래프를 포함하는 카드 (멤버 리스트를 인자로 받도록 수정) */
@Composable
public fun ProgressBarChartCard(members: List<MemberProgress>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("100%", fontSize = 12.sp, color = Color.Black)
                    Text("75%", fontSize = 12.sp, color = Color.Black)
                    Text("50%", fontSize = 12.sp, color = Color.Black)
                    Text("25%", fontSize = 12.sp, color = Color.Black)
                    Text("0%", fontSize = 12.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.width(8.dp))

                // 막대그래프
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    members.forEach { member ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .width(30.dp)
                                    .fillMaxHeight(fraction = member.progress)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(Color.LightGray)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(member.name, fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
