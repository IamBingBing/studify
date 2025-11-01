package com.example.studify.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.studify.Tool.routes

@Composable
fun currentRoute ( navController: NavHostController): String? {
    val selectedDestination by navController.currentBackStackEntryAsState()
    return selectedDestination?.destination?.route
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier,
                  navController: NavHostController = rememberNavController()
                ) {
    val navController = rememberNavController()
    val items = mapOf(
        routes.matchMenu to "매칭",
        routes.group to "그룹",
        routes.shop to "상점",
        routes.chatlist to "채팅방",
        routes.mypage to "마이페이지"
    )
    /*
    Scaffold( bottomBar = {
        NavigationBar (windowInsets = NavigationBarDefaults.windowInsets){ items.forEach { routes, s -> NavigationBarItem(selected = currentRoute()) } }
    }) {

    }*/
}
