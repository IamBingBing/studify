package com.example.studify.Tool

import androidx.compose.ui.graphics.painter.Painter
import com.example.studify.R

enum class routes(val route: String, val label: String,val icon:Int, val contentDescription : String?){

    group("grouphome", label="그룹",R.drawable.logo ,null),
    matchMenu("matching", label="매칭",R.drawable.logo ,null),
    shop("shop", label="상점",R.drawable.logo ,null),
    mypage("mypage", label="내 정보",R.drawable.logo ,null),
    chatlist("chatlist", label="채팅",R.drawable.logo ,null)
}