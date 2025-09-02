package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.hismc.ui.theme.HismcTheme

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(top = 100.dp, start = 50.dp)
                .width(200.dp)
                .height(300.dp)
                .background(color = Color.Gray)
                ,
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview2(){
    HismcTheme {
        HomeScreen(navController = rememberNavController())
    }
}