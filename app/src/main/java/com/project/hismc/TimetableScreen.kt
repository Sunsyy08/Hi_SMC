package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.hismc.timetable.TimetableViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TimetableScreen(navController: NavController, apiKey: String, viewModel: TimetableViewModel = viewModel()) {
    val timetable by viewModel.timetable.collectAsState()

    // 세련된 파란색 컬러 팔레트
    val primaryBlue = Color(0xFF1E3A8A)      // 진한 파란색
    val accentBlue = Color(0xFF3B82F6)       // 밝은 파란색
    val lightBlue = Color(0xFFDBEAFE)        // 연한 파란색
    val headerBlue = Color(0xFF1E40AF)       // 헤더용 파란색
    val evenRowColor = Color(0xFFF1F8FF)     // 짝수 행 배경색
    val textDark = Color(0xFF1F2937)         // 진한 텍스트
    val textLight = Color.White              // 밝은 텍스트

    LaunchedEffect(Unit) {
        viewModel.loadTimetable(apiKey)
    }

    if (timetable.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(lightBlue, Color.White)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.padding(32.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "시간표를 불러오는 중...",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        color = textDark
                    )
                }
            }
        }
        return
    }

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val daysOfWeek = listOf("월","화","수","목","금")

    val dateToDay = timetable.keys.associateWith {
        LocalDate.parse(it, formatter).dayOfWeek.value  // 1=월, 7=일
    }

    // 선택과목 합치기
    val processedTable = timetable.mapValues { entry ->
        val periodMap = mutableMapOf<Int, MutableList<String>>()
        entry.value.forEachIndexed { index, content ->
            val period = index + 1
            if (periodMap.containsKey(period)) {
                periodMap[period]?.add(content)
            } else {
                periodMap[period] = mutableListOf(content)
            }
        }
        (1..periodMap.keys.maxOrNull()!!)
            .map { period -> periodMap[period]?.joinToString(" / ") ?: "" }
    }

    // 최소 7교시까지 표시
    val maxPeriods = maxOf(processedTable.values.maxOfOrNull { it.size } ?: 0, 7)

    NavDrawer(navController = navController, schoolName = "세명컴퓨터고등학교") {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(lightBlue, Color.White)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // 제목
                    Text(
                        text = "📚 시간표",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryBlue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    // 헤더 (요일)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(50.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(primaryBlue, headerBlue)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "교시",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = textLight
                            )
                        }

                        daysOfWeek.forEachIndexed { index, day ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .clip(
                                        if (index == daysOfWeek.size - 1)
                                            RoundedCornerShape(topEnd = 12.dp)
                                        else
                                            RoundedCornerShape(0.dp)
                                    )
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(accentBlue, primaryBlue)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    day,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textLight
                                )
                            }
                        }
                    }

                    // 교시별 행
                    for (period in 1..maxPeriods) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(65.dp)
                                    .background(
                                        if (period == maxPeriods) {
                                            Brush.horizontalGradient(
                                                colors = listOf(primaryBlue.copy(alpha = 0.8f), headerBlue.copy(alpha = 0.8f))
                                            )
                                        } else {
                                            Brush.horizontalGradient(
                                                colors = listOf(primaryBlue.copy(alpha = 0.9f), headerBlue.copy(alpha = 0.9f))
                                            )
                                        }
                                    )
                                    .let {
                                        if (period == maxPeriods)
                                            it.clip(RoundedCornerShape(bottomStart = 12.dp))
                                        else it
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "${period}교시",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = textLight
                                )
                            }

                            for (dayIndex in 1..5) {
                                val date = dateToDay.filter { it.value == dayIndex }.keys.firstOrNull()
                                val subjects = date?.let { processedTable[it] } ?: emptyList()
                                val text = subjects.getOrNull(period - 1) ?: ""

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(65.dp)
                                        .background(
                                            if (period % 2 == 0) evenRowColor else Color.White
                                        )
                                        .let {
                                            if (period == maxPeriods && dayIndex == 5)
                                                it.clip(RoundedCornerShape(bottomEnd = 12.dp))
                                            else it
                                        }
                                        .padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = text,
                                        fontSize = 13.sp,
                                        fontWeight = if (text.isNotEmpty()) FontWeight.Medium else FontWeight.Normal,
                                        color = if (text.isNotEmpty()) textDark else Color.Gray,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        lineHeight = 16.sp,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}