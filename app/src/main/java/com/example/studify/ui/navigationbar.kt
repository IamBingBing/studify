package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun navigationbar(
                  navController: NavHostController
                ) {
    var current by remember { mutableStateOf("") }

    Scaffold( bottomBar = {
        NavigationBar (windowInsets = NavigationBarDefaults.windowInsets){
            NavigationBarItem(
                selected = current == "grouphome",
                onClick = {
                    navController.navigate("groupHome")
                    current == "grouphome"
                          },
                icon = {
                    Icon(Icons.Default.Home, contentDescription = null)
                       },
                label = { Text("groupHome") }
            )


            NavigationBarItem(
                selected = current == "matchMenu",
                onClick = {
                    navController.navigate("matchMenu")
                    current == "matchMenu"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("matchMenu") }
            )
            NavigationBarItem(
                selected = current == "shop",
                onClick = {
                    navController.navigate("shop")
                    current == "shop"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("shop") }
            )
            NavigationBarItem(
                selected = current == "mypage",
                onClick = {
                    navController.navigate("mypage")
                    current == "mypage"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("mypage") }
            )
            NavigationBarItem(
                selected = current == "chatlist",
                onClick = {
                    navController.navigate("chatlist")
                    current == "chatlist"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("chatlist") }
            )
        }
    }){
    }
}

