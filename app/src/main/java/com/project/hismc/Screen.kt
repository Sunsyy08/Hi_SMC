package com.project.hismc

sealed class Screen(val route : String) {
    object Start : Screen("start")
    object SignUp : Screen("signup")
    // ✅ SignIn 화면에 major 파라미터 추가
    object SignIn : Screen("signin")
    object Home : Screen("home")
    object Profile : Screen("ProfileScreen")
    object Timetable : Screen("TimeTableScreen")
    object LostItems : Screen("LostItemsScreen")
}
