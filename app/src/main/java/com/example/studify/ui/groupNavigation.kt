package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@ExperimentalMaterial3Api
@Composable
fun groupNavigation(vm: groupVM= hiltViewModel(), navController: NavController) {

        val groupName by vm.groupName
        val groupId = vm.groupId.value.toString()

        val tabs = listOf("groupHome", "calender", "member", "notice", "progress")

        var selectedTab by vm.currentTab

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🏷️ 그룹 제목
            Text(
                text = groupName,
                fontSize = 30.sp,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // 🔽 탭 UI
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {

                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = index == selectedTab,
                        onClick = {
                            navController.navigate("${tabs[index]}/$groupId")
                            selectedTab = index
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 15.sp,
                                style = if (index == selectedTab)
                                    MaterialTheme.typography.titleMedium
                                else MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }

        }


}
