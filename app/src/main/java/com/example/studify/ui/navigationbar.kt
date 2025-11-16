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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.NavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun navigationbar(navController: NavController) {
    var current by remember { mutableStateOf("") }


        NavigationBar (windowInsets = NavigationBarDefaults.windowInsets ){
            NavigationBarItem(
                selected = current == "grouphome",
                onClick = {
                    navController.navigate("groupHome"){
                        popUpTo("groupHome"){
                            inclusive = true
                        }
                    }
                    current = "grouphome"
                          },
                icon = {
                    Icon(Icons.Default.Home, contentDescription = null)
                       },
                label = { Text("groupHome") }
            )


            NavigationBarItem(
                selected = current == "matchMenu",
                onClick = {
                    navController.navigate("matchMenu"){
                        popUpTo("groupHome"){
                            inclusive = true
                        }
                    }
                    current ="matchMenu"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("matchMenu") }
            )
            NavigationBarItem(
                selected = current == "shop",
                onClick = {
                    navController.navigate("shop"){
                        popUpTo("groupHome"){
                            inclusive = true
                        }
                    }
                    current = "shop"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("shop") }
            )
            NavigationBarItem(
                selected = current == "mypage",
                onClick = {
                    navController.navigate("mypage"){
                        popUpTo("groupHome"){
                            inclusive = true
                        }
                    }
                    current = "mypage"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("mypage") }
            )
            NavigationBarItem(
                selected = current == "chatlist",
                onClick = {
                    navController.navigate("chatlist"){
                        popUpTo("groupHome"){
                            inclusive = true
                        }
                    }
                    current = "chatlist"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("chatlist") }
            )
        }

}

