package com.example.studify.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import com.example.studify.ui.login
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.studify.Tool.MatchingCase
import com.example.studify.ui.Guideline
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
//import com.example.studify.ui.progress
import com.example.studify.ui.qna
import com.example.studify.ui.register
import com.example.studify.ui.shop
import com.example.studify.ui.writeArticle
import com.example.studify.ui.createDate
import com.example.studify.ui.grouplist
import com.example.studify.ui.member
import com.example.studify.ui.progress
import com.example.studify.ui.searchbook

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
        composable(route="chatingRoom/{roomid}", arguments = listOf(
            navArgument("roomid"){
                type = NavType.StringType
            }
        )){
            entry -> val roomid = entry.arguments?.getString("roomid")
            chatingRoom(navController = navController)
        }
        composable(route="chatlist"){
            
            chatlist(navController = navController)
        }
        composable(route="createGroup"){
            createGroup(navController = navController)
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
        composable(route="mentor{mentorid}", arguments = listOf(
            navArgument("mentorid"){
                type = NavType.StringType
            }
        )){
            entry -> val mentorid = entry.arguments?.getString("mentorid")
            mentor(navController = navController)
        }
        composable(route="mypage"){
            
            mypage(navController = navController)
        }
        composable(route= "notice/{groupid}", arguments = listOf(
            navArgument("groupid"){
                type = NavType.StringType
            }
        )){
                entry -> val groupid = entry.arguments?.getString("groupid")
            notice(navController = navController)
        }
        composable(route="noticeDetail/{noticeid}", arguments = listOf(
            navArgument("noticeid"){
                type = NavType.StringType
            }
        )){
            entry -> val noticeid = entry.arguments?.getString("noticeid")
            noticeDetail(navController = navController)
        }
        composable(
            route = "productDetail/{goodId}", arguments = listOf(
                navArgument("goodId") { type = NavType.IntType }
            )
        ) { entry ->
            val goodId = entry.arguments?.getInt("goodId") ?: 0
            productDetail(navController = navController, goodId = goodId)
        }
        composable(route="profilepage/{userId}",
            arguments = listOf(
                navArgument("userId") {type = NavType.StringType}
            )
        ){
            profilepage(navController = navController)
        }
        composable(route= "progress/{groupid}", arguments = listOf(
            navArgument("groupid"){
                type = NavType.StringType
            }
        )
        ){
                entry -> val groupid = entry.arguments?.getString("groupid")
            progress(navController = navController)
        }
        composable(route="qna"){
            qna(navController = navController)
        }
        composable(route="shop"){
            shop(navController = navController)
        }
        composable(
            route = "writeArticle/{groupid}",
            arguments = listOf(
                navArgument("groupid") { type = NavType.StringType }
            )
        ) {
            writeArticle(navController = navController)
        }
        composable(route= "groupHome/{groupid}", arguments = listOf(
            navArgument("groupid"){
                type = NavType.StringType
            }
        )){
            entry -> val groupid = entry.arguments?.getString("groupid")
            groupHome(navController = navController)
        }
        composable(route= "calender/{groupid}", arguments = listOf(
            navArgument("groupid"){
                type = NavType.StringType
            }
        )){
                entry -> val groupid = entry.arguments?.getString("groupid")
            calender (navController = navController)
        }
        composable(route= "createDate/{groupid}", arguments = listOf(
            navArgument("groupid"){
                type = NavType.StringType
            }
        )){
                entry -> val groupid = entry.arguments?.getString("groupid")
            createDate(navController = navController)
        }
        composable (route= "grouplist"){
            grouplist(navController = navController)
        }
        composable(route= "member/{groupid}", arguments = listOf(
            navArgument("groupid"){
                type = NavType.StringType
            }
        )){
                entry -> val groupid = entry.arguments?.getString("groupid")
            member(navController = navController)
        }
        composable(
            route = "guideline/{groupGoal}",
            arguments = listOf(navArgument("groupGoal") { type = NavType.StringType })
        ) {
            Guideline(navController = navController)
        }
        composable(route = "searchbook") {
            // searchbook 컴포저블 함수 호출 (import 필요)
            searchbook(navController = navController)
        }
    }
}