package com.project.hismc


import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController) {
    NavDrawer(navController = navController,schoolName = "세명컴퓨터고등학교") {

    }
}
