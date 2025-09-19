package com.project.hismc


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.hismc.ui.theme.HismcTheme

@Composable
fun ProfileScreen(navController: NavController) {
    NavDrawer(navController = navController,schoolName = "세명컴퓨터고등학교") {

    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        )
    {
        Text(
            text = "박선혁",  // 이름
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = "인공지능소프트웨어과",  // 학과
            fontSize = 22.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
fun ProfilePreview(){
    HismcTheme {
        ProfileScreen(navController = rememberNavController())
    }
}
