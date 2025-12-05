package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
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


        NavigationBar (containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
            windowInsets = NavigationBarDefaults.windowInsets ){
            NavigationBarItem(
                selected = current == "grouplist",
                onClick = {
                    navController.navigate("grouplist"){
                        popUpTo("groupHome")
                    }
                    current = "grouplist"
                          },
                icon = {
                    Icon(Icons.Default.Home, contentDescription = null)
                       },
                label = { Text("그룹") }
            )


            NavigationBarItem(
                selected = current == "matchMenu",
                onClick = {
                    navController.navigate("matchMenu"){
                        popUpTo("groupHome")
                    }
                    current ="matchMenu"
                },
                icon = { Icon(Icons.Default.Person , contentDescription = null) },
                label = { Text("매칭") }
            )
            NavigationBarItem(
                selected = current == "shop",
                onClick = {
                    navController.navigate("shop"){
                        popUpTo("groupHome")
                    }
                    current = "shop"
                },
                icon = { Icon(Icons.Default.ShoppingCart , contentDescription = null) },
                label = { Text("상점") }
            )
            NavigationBarItem(
                selected = current == "mypage",
                onClick = {
                    navController.navigate("mypage"){
                        popUpTo("groupHome")
                    }
                    current = "mypage"
                },
                icon = { Icon(Icons.Default.Info , contentDescription = null) },
                label = { Text("마이페이지") }
            )
            NavigationBarItem(
                selected = current == "chatlist",
                onClick = {
                    navController.navigate("chatlist"){
                        popUpTo("groupHome")
                    }
                    current = "chatlist"
                },
                icon = { Icon(Icons.Default.Email , contentDescription = null) },
                label = { Text("채팅") }
            )
        }

}

