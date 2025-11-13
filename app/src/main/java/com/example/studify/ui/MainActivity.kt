package com.example.studify.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.studify.Tool.MatchingCase
import com.example.studify.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import java.io.Console
import java.util.logging.Logger

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
         setContent {
             val navController = rememberNavController()
             AppNavHost(navController=navController)
         }
    }
}