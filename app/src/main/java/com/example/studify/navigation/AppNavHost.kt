package com.example.studify.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studify.ui.login

@Composable
fun AppNavHost(navController: NavController, modifier: Modifier) {
    /*NavHost(navController = navController, startDestination = "login", modifier = modifier,graph= ){
        composable("login") {
            login(nav = navController)
        }
    }*/
}