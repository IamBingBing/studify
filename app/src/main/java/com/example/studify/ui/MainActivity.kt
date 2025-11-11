package com.example.studify.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.studify.Tool.MatchingCase


class MainActivity : androidx.activity.ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
<<<<<<< Updated upstream
            //matchMenu()
            //navigation()
            noticePage()
=======
            login(loginVM())
>>>>>>> Stashed changes
        }
    }
}