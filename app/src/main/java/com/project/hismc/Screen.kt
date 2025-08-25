package com.project.hismc

sealed class Screen(val route : String) {
    object Start : Screen("start")
}