package com.example.studify.Tool

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object BaseModifiers {
    val BaseBoxModifier = Modifier.fillMaxWidth().padding(top = 25.dp)
    val BaseBtnModifier = Modifier.padding(10.dp)
    val BaseArticleModifier = Modifier
    val BaseTextfillModifier = Modifier.padding(10.dp)
    val BaseModifier = Modifier

    val BaseChatModifier = Modifier.background(color = Color(0xFF3CA0FF), shape = RoundedCornerShape(8.dp))

    val DialogCard = Modifier

}