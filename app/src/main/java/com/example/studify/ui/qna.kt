package com.example.studify.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@ExperimentalMaterial3Api
@Composable

fun qna(vm: qnaVM = hiltViewModel(), navController: NavController) {

    val qna_id = vm.qna_id
    val qna_title = vm.qna_title
    val qna_content = vm.qna_content
    val qna_answer = vm.qna_answer
    val itemList = vm.items.toList()

    Scaffold(
        topBar = { groupNavigation(navController = navController) },
        bottomBar = { navigationbar(navController) }
    ) { paddingValues ->

        Surface(
            modifier = BaseModifiers.BaseModifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.White
        ) {

            Column(
                modifier = BaseModifiers.BaseModifier.fillMaxSize()
            ) {

            }

            Row {
                Box(
                    modifier = BaseModifiers.BaseBoxModifier
                        .background(color = Color.Black)
                ) {
                    Text(
                        text = "QNA",
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                        modifier = BaseModifiers.BaseModifier
                    )
                }
            }
        }
    }
}
