package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController) {
    NavDrawer(navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2))
                .padding(16.dp)
        ) {
            Text(
                text = "프로필 화면",
                fontSize = 28.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(text = "이름: 박선혁", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "학년: 2학년", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "이메일: example@school.com", fontSize = 20.sp)
        }
    }
}
