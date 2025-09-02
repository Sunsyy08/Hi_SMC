package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.hismc.viewmodel.MealViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val mealViewModel: MealViewModel = viewModel()
    val meals by mealViewModel.mealInfo.collectAsState()
    val school by mealViewModel.schoolInfo.collectAsState()

    val today = java.time.LocalDate.now().toString().replace("-", "")

    LaunchedEffect(Unit) {
        val officeCode = "B10" // 서울특별시교육청
        val schoolCode = "7010537" // 예시 학교 코드
        val mealCode = "2" // 중식

    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(top = 100.dp, start = 50.dp)
                .width(300.dp)
                .height(500.dp)
                .background(color = Color.Gray)
                .padding(8.dp)
        ) {
            Column {
                // 학교 정보
                school?.let {
                    Text(text = "학교명: ${it.SCHUL_NM ?: "정보 없음"}")
                    Text(text = "주소: ${it.ORG_RDNMA ?: "정보 없음"}")
                    Text(text = "전화: ${it.ORG_TELNO ?: "정보 없음"}")
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // 급식 정보
                if (meals.isEmpty()) {
                    Text(text = "오늘 급식 정보가 없습니다.")
                } else {
                    meals.forEach { meal ->
                        Text(text = "날짜: ${meal.MLSV_YMD ?: ""}")
                        Text(text = meal.DDISH_NM?.replace("<br/>", "\n") ?: "")
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}
