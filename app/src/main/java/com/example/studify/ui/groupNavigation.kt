package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.Color

private val TopNavColor = Color(0xFF6BB8C2)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun groupNavigation(
    vm: groupVM = hiltViewModel(),
    navController: NavController
) {
    val groupName by vm.groupName
    val groupId = vm.groupId.value.toString()

    // (route, label)
    val tabs = listOf(
        "groupHome" to "홈",
        "calender" to "캘린더",
        "member" to "멤버",
        "notice" to "공지",
        "progress" to "진행도"
    )

    // 🔹 현재 네비게이션 상태에서 route 가져오기
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // 🔹 현재 route 에 맞는 인덱스 찾기
    val selectedIndex = tabs.indexOfFirst { (route, _) ->
        currentRoute?.startsWith(route) == true   // "groupHome/{id}" 같은 경우 고려
    }.let { if (it == -1) 0 else it }             // 못 찾으면 0(홈)

    Surface(
        color = TopNavColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = groupName,
                fontSize = 30.sp,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            TabRow(
                selectedTabIndex = selectedIndex,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, (route, label) ->
                    Tab(
                        selected = index == selectedIndex,
                        onClick = {
                            // 이미 그 탭에 있으면 또 navigate 안 해도 됨
                            if (index != selectedIndex) {
                                navController.navigate("$route/$groupId") {
                                    launchSingleTop = true
                                }
                            }
                        },
                        text = {
                            Text(
                                text = label,
                                fontSize = 15.sp,
                                style = if (index == selectedIndex)
                                    MaterialTheme.typography.titleMedium
                                else MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        }
    }
}
