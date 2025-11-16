package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.studify.Tool.routes


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(
                  navController: NavHostController
                ) {
    val startdestination = routes.group


    Scaffold( bottomBar = {
        NavigationBar (windowInsets = NavigationBarDefaults.windowInsets){
            NavigationBarItem(
                selected = current == "home",
                onClick = { navController.navigate("home") },
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text("Home") }
            )

            NavigationBarItem(
                selected = current == "profile",
                onClick = { navController.navigate("profile") },
                icon = { Icon(Icons.Default.Person, contentDescription = null) },
                label = { Text("Profile") }
            )
        }
    }){
    }
}

