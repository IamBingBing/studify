package com.example.studify.Tool

import androidx.compose.ui.graphics.painter.Painter
import com.example.studify.R

enum class routes(val route: String, val label: String,val icon:Int, val contentDescription : String?){

    group("com.example.studify.ui.group", label="그룹",R.drawable.logo ,null),
    matchMenu("com.example.studify.ui.matchMenu", label="매칭",R.drawable.logo ,null),
    shop("com.example.studify.ui.shop", label="상점",R.drawable.logo ,null),
    mypage("com.example.studify.mypage", label="내 정보",R.drawable.logo ,null),
    chatlist("com.example.studify.chat", label="채팅",R.drawable.logo ,null)
}