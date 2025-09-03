package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.hismc.viewmodel.MealViewModel
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = {3})
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(top = 100.dp, start = 50.dp)
                .width(340.dp)
                .height(250.dp)
                .background(color = Color.White)
                .padding(8.dp),
        ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = pagerState,
                ) { page ->
                    val text = when (page) {
                        0 -> "선혁"
                        1 -> "선호"
                        2 -> "개"
                        else -> ""
                    }
                    Card(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .height(220.dp)
                            .width(300.dp)
                            .padding(20.dp),
                    ) {
                        Text(
                            text = text,
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }

//                Card(
//                    colors = CardDefaults.cardColors(
//                        contentColor = MaterialTheme.colorScheme.surfaceVariant
//                    ),
//                    modifier = Modifier
//                        .size(width = 120.dp, height = 100.dp)
//                ) {
//                    Text(
//                        text = "선호",
//                        color = Color.Black,
//                        modifier = Modifier
//                            .padding(16.dp),
//                        textAlign = TextAlign.Center
//                    )
//                }
//                Spacer(modifier = Modifier.height(22.dp))
//                Card(
//                    colors = CardDefaults.cardColors(
//                        contentColor = MaterialTheme.colorScheme.surfaceVariant
//                    ),
//                    modifier = Modifier
//                        .size(width = 120.dp, height = 100.dp)
//                ) {
//                    Text(
//                        text = "선혁",
//                        color = Color.Black,
//                        modifier = Modifier
//                            .padding(16.dp),
//                        textAlign = TextAlign.Center
//                    )
//                }
            }
        }
    }
}
