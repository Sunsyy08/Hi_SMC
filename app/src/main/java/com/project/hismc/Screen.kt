package com.project.hismc

sealed class Screen(val route : String) {
    object Start : Screen("start")
    object SignUp : Screen("signup")
    // ✅ SignIn 화면에 major 파라미터 추가
    object SignIn : Screen("signin?major={major}") {
        fun createRoute(major: String? = null): String {
            return if (major != null) "signin?major=$major" else "signin"
        }
    }

    object Home : Screen("home/{major}") {
        fun createRoute(major: String) = "home/$major"
    }
    object Profile : Screen("ProfileScreen")
    object Timetable : Screen("TimeTableScreen")
}
