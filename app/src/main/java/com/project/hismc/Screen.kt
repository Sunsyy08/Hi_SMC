package com.project.hismc

sealed class Screen(val route : String) {
    object Start : Screen("start")
    object SignUp : Screen("signup")
    object SignIn : Screen("signin")
}
