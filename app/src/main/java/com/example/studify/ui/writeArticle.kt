package com.example.studify.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
fun writeArticle ( vm : writeArticleVM = viewModel(), navController: NavController){
    var title by remember { mutableStateOf<String>("") }
    var content by remember { mutableStateOf<String>("") }
    Column (modifier = BaseModifiers.BaseBoxModifier){

        Text("제목")
        TextField(value = title , onValueChange = {title = it  },
            modifier= BaseModifiers.BaseTextfillModifier.align(alignment = Alignment.CenterHorizontally) )

        Text("내용")
        TextField(value= content, onValueChange = {content = it} ,
            modifier= BaseModifiers.BaseTextfillModifier.height(300.dp).align(alignment = Alignment.CenterHorizontally))
    }
}