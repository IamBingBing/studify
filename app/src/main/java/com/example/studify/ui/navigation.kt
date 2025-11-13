package com.example.studify.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.studify.Tool.routes


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(modifier: Modifier = Modifier,
                  navController: NavHostController = rememberNavController()
                ) {
    val navController = rememberNavController()
    val startdestination = routes.group
    var selectedDestination by rememberSaveable { mutableIntStateOf(startdestination.ordinal) }


    Scaffold( bottomBar = {
        NavigationBar (windowInsets = NavigationBarDefaults.windowInsets){
            routes.entries.forEachIndexed { index, routes ->
            NavigationBarItem(selected = selectedDestination == index,
                onClick = { navController.navigate(route= routes.route)
                    selectedDestination = index
                } ,
                icon = {
                    Icon(
                        painter = painterResource( routes.icon),
                        contentDescription = routes.contentDescription
                    )
                },
                label = {
                    Text(routes.label)
                })
            }
        }

    }){
    }
}

