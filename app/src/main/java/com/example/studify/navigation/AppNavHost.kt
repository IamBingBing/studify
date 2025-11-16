package com.example.studify.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.studify.ui.login
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.studify.Tool.MatchingCase
import com.example.studify.ui.MatchingIng
import com.example.studify.ui.calender
import com.example.studify.ui.chatingRoom
import com.example.studify.ui.chatlist
import com.example.studify.ui.createGroup
import com.example.studify.ui.groupHome
import com.example.studify.ui.groupNavigation
//import com.example.studify.ui.groupPage
import com.example.studify.ui.matchMenu
import com.example.studify.ui.matchingOptionFast
import com.example.studify.ui.matchingOptionGroup
import com.example.studify.ui.matchingOptionMentor
import com.example.studify.ui.matchingcomplete
import com.example.studify.ui.mentor
import com.example.studify.ui.mypage
import com.example.studify.ui.navigationbar
import com.example.studify.ui.notice
import com.example.studify.ui.noticeDetail
import com.example.studify.ui.productDetail
import com.example.studify.ui.profilepage
import com.example.studify.ui.progress
import com.example.studify.ui.qna
import com.example.studify.ui.register
import com.example.studify.ui.shop
import com.example.studify.ui.writeArticle

@Composable
@ExperimentalMaterial3Api
fun AppNavHost(navController: NavHostController) {
    NavHost(navController= navController, startDestination = "login") {
        composable(route="login"){
            login(navController = navController)
        }
        composable(route="register"){
            register(navController = navController)
        }
        composable(route="chatingRoom"){
            chatingRoom(navController = navController)
        }
        composable(route="chatlist"){
            
            chatlist(navController = navController)
        }
        composable(route="createGroup"){
            createGroup(navController = navController)
        }
        composable(route="group"){
            //groupPage(navController = navController)
        }
        composable(route="matchingComplete"){
            matchingcomplete(navController = navController)
        }
        composable(route="Matchinging"){
            MatchingIng(navController = navController)
        }
        composable(route="matchingOptionFast"){
            matchingOptionFast(navController = navController)
        }
        composable(route="matchingOptionGroup"){
            matchingOptionGroup(navController = navController)
        }
        composable(route="matchingOptionMentor"){
            matchingOptionMentor(navController = navController)
        }
        
        composable(route="matchMenu"){
            
            matchMenu(navController = navController)
        }
        composable(route="mentor"){
            mentor(navController = navController)
        }
        composable(route="mypage"){
            
            mypage(navController = navController)
        }
        composable(route="notice"){
            
            notice(navController = navController)
        }
        composable(route="noticeDetail"){
            noticeDetail(navController = navController)
        }
        composable(route="productDetail"){
            productDetail(navController = navController)
        }
        composable(route="profilepage"){
            profilepage(navController = navController)
        }
        composable(route="progress"){
            
            progress(navController = navController)
        }
        composable(route="qna"){
            qna(navController = navController)
        }
        composable(route="shop"){
            shop(navController = navController)
        }
        composable(route="writeArticle"){
            writeArticle(navController = navController)
        }
        composable(route= "groupHome"){
            
            groupHome(navController = navController)
        }
        composable(route= "calender"){
            calender(navController = navController)
        }
    }
}