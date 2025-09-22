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
            // 회원가입 완료 시 userViewModel.setMajor(선택한 학과) 해주면 됨
            SignUpScreen(navController = navController, userViewModel = userViewModel)
        }

        composable(Screen.SignIn.route) {
            // 로그인에서 필요하면 userViewModel.setMajor(...) 가능
            SignInScreen(navController = navController, userViewModel = userViewModel)
        }

        composable(Screen.Home.route) {
            // Home 화면에서는 userViewModel에서 major 불러오기
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
