package com.example.studify.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@ExperimentalMaterial3Api
@Composable
fun groupNavigation(vm: groupVM= hiltViewModel(),
          navController: NavController) {
    var groupName by vm.groupName
    val selectedTab = mapOf(0 to "home", 1 to "calender",2 to "member",3 to "notice", 4 to "progress")
    var currentTab by vm.currentTab //화면이 리컴포즈될때마다 0으로 초기화돼서 탭이 자꾸 홈으로 이동하는 거 방지



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
                            navController.navigate(selectedTab.getValue(tab))
                        },
                        text = {
                            Text(
                                selectedTab.getValue(tab)
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))


        }
    }
}
