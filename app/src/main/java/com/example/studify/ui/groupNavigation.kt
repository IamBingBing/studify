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
    var groupName by vm.groupName
    val selectedTab = mapOf(0 to "home", 1 to "calender",2 to "member",3 to "notice", 4 to "progress")
    var currentTab by vm.currentTab
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = BaseModifiers.BaseModifier.padding(top = 15.dp)) {
            Text(groupName , fontSize = 40.sp)
            TabRow(selectedTabIndex = currentTab) {
                selectedTab.keys.forEach { tab ->
                    Tab(
                        selected = tab == currentTab,
                        onClick = {
                            navController.navigate(selectedTab.getValue(tab))
                            currentTab = tab
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
