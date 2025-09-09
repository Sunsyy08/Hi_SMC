package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    LaunchedEffect(Unit) {
        viewModel.loadTimetable(apiKey)
    }

    if (timetable.isEmpty()) {
        Text("시간표를 불러오는 중...")
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

    NavDrawer(navController = navController){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp) // 카드 높이 충분히 확보
                .padding(12.dp)
        ) {
            // 헤더 (요일)
            Row {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(60.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("교시", fontSize = 16.sp)
                }

                for (day in daysOfWeek) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .border(1.dp, Color.Black),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(day, fontSize = 16.sp)
                    }
                }
            }

            // 교시별 행
            for (period in 1..maxPeriods) {
                Row {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(60.dp)
                            .border(1.dp, Color.Black),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text("${period}교시", fontSize = 16.sp)
                    }

                    for (dayIndex in 1..5) {
                        val date = dateToDay.filter { it.value == dayIndex }.keys.firstOrNull()
                        val subjects = date?.let { processedTable[it] } ?: emptyList()
                        val text = subjects.getOrNull(period - 1) ?: ""

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                                .border(1.dp, Color.Black)
                                .background(if (period % 2 == 0) Color(0xFFEFEFEF) else Color.White),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(text, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

