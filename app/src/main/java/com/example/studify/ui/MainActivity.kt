package com.example.studify.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.studify.Tool.MatchingCase
import com.example.studify.data.repository.ChatRepository
import com.example.studify.di.ChatWebSocketClient
import com.example.studify.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.Console
import java.util.logging.Logger
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalMaterial3Api
class MainActivity : androidx.activity.ComponentActivity(){
    @Inject lateinit var chatRepository: ChatRepository
    @Inject lateinit var chatWebSocketClient: ChatWebSocketClient
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

         setContent {

             val navController = rememberNavController()
             AppNavHost(navController=navController)
         }

    }

    override fun onStart() {
        super.onStart()
        chatWebSocketClient.connect()
        chatWebSocketClient.sendLogin()
    }


}