package com.example.studify.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studify.Tool.BaseModifiers

@Composable
@Preview
fun matchingcomplete(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White)
     {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
          Row(){
              Box(
                  modifier = BaseModifiers.BaseModifier
                      .width(50.dp).height(50.dp)
                      .background(color = Color.Red),
                  contentAlignment = Alignment.Center



              ){Text ("상대방")}
              Spacer(modifier = Modifier.padding(10.dp))
              Box(
                  modifier = BaseModifiers.BaseModifier
                      .width(50.dp).height(50.dp)
                      .background(color = Color.Blue),
                  contentAlignment = Alignment.Center

              ){Text ("본인")}


          }


            Text(text = "매칭 완료되었습니다")
            Button(modifier = BaseModifiers.BaseBtnModifier, onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                )
            ) {
                Text(text = "채팅방으로 아동")
            }
        }
    }

}