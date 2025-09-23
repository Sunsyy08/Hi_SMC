package com.project.hismc

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.hismc.UserViewModel

@Composable
fun SMCNavHost(navController: NavHostController) {
    // 🔹 NavHost 범위에서 UserViewModel 생성
    val userViewModel: UserViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Start.route) {

        composable(Screen.Start.route) {
            StartScreen(navController = navController)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController, userViewModel = userViewModel)
        }

        composable(Screen.SignIn.route) {
            SignInScreen(navController = navController, userViewModel = userViewModel)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController, userViewModel = userViewModel)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(Screen.Timetable.route) {
            TimetableScreen(
                navController = navController,
                apiKey = "df0ad9860d1c49618a5f8de265a5c621"
            )
        }
    }
}
