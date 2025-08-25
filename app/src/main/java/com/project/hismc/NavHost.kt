package com.project.hismc

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

@Composable
fun SMCNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screen.Start.route){

        composable(Screen.Start.route){
            StartScreen(navController = navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}