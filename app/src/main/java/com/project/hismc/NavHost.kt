package com.project.hismc

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SMCNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Start.route){

        composable(Screen.Start.route){
            StartScreen(navController = navController)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }

        // ✅ SignIn 화면에 major 파라미터 추가
        composable(
            route = "signin?major={major}",  // Screen.SignIn.route 대신 직접 작성
            arguments = listOf(
                navArgument("major") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val major = backStackEntry.arguments?.getString("major")
            SignInScreen(navController = navController, major = major)
        }

        composable(
            route = Screen.Home.route,
            arguments = listOf(navArgument("major") { type = NavType.StringType })
        ) { backStackEntry ->
            val major = backStackEntry.arguments?.getString("major") ?: "정보 없음"
            HomeScreen(navController = navController, major = major)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(Screen.Timetable.route) {
            TimetableScreen(
                navController = navController,
                apiKey = "df0ad9860d1c49618a5f8de265a5c621")
        }
    }
}