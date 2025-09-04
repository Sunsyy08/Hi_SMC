package com.project.hismc

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
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
    val images = listOf(
        R.drawable.smc_ms2,
        R.drawable.smc_ms
    )
    val pagerState = rememberPagerState(pageCount = { images.size })
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(top = 100.dp, start = 50.dp)
                .width(340.dp)
                .height(250.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .padding(8.dp),
        ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(16.dp),
                    state = pagerState,
                ) { currentPage ->
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(12.dp))
                            .height(240.dp)
                            .width(330.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Image(painter = painterResource(id = images[currentPage]), contentDescription = "")
                    }
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 380.dp, start = 50.dp)
                .width(340.dp)
                .height(300.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                ),
        ){

        }
    }
}
