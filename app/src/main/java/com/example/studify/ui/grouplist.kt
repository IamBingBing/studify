package com.example.studify.ui
import android.service.autofill.OnClickAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.studify.Tool.BaseModifiers

@Composable
fun grouplist(vm : chatlistVM = hiltViewModel() ,  navController: NavController){
    var error = vm.error
    var grouplist = vm.chatlist
    if (error.value != "") {
        AlertDialog(
            onDismissRequest = { error.value =""},
            title = { Text("오류") },
            text = { Text(error.value?: "") },
            confirmButton = {
                TextButton (onClick = { error.value=""}) {
                    Text("확인")
                }
            }
        )
    }
    Scaffold (topBar = { Text("그룹 목록") }, bottomBar= { navigationbar(navController) }){
            innerpadding->
        Column(modifier= BaseModifiers.BaseBoxModifier.padding(innerpadding).fillMaxSize()) {
            Box(BaseModifiers.BaseBoxModifier) { Text("그룹 목록") }

            LazyColumn(modifier = BaseModifiers.BaseBoxModifier) {
                grouplist.forEach({
                        group->
                    item {
                        Text(text = group.value, modifier= BaseModifiers.BaseChatModifier.clickable {
                            navController.navigate("groupHome/${group.key}")
                        }) }
                })

            }
        }
    }
}