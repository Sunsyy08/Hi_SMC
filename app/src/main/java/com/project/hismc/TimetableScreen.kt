package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.hismc.timetable.TimetableViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TimetableScreen(apiKey: String, viewModel: TimetableViewModel = viewModel()) {
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

    // 날짜 → 요일 매핑
    val dateToDay = timetable.keys.associateWith {
        LocalDate.parse(it, formatter).dayOfWeek.value  // 1=월, 7=일
    }

    // 최대 교시 계산
    val maxPeriods = timetable.values.maxOfOrNull { it.size } ?: 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        // 헤더 (요일)
        Row {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(40.dp)
                    .border(1.dp, Color.Black),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("교시", fontSize = 14.sp)
            }

            for (day in daysOfWeek) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(day, fontSize = 14.sp)
                }
            }
        }

        // 교시별 행
        for (period in 1..maxPeriods) {
            Row {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(50.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("${period}교시", fontSize = 14.sp)
                }

                for (dayIndex in 1..5) {
                    val date = dateToDay.filter { it.value == dayIndex }.keys.firstOrNull()
                    val subjects = date?.let { timetable[it] } ?: emptyList()
                    val text = subjects.getOrNull(period - 1) ?: ""

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .border(1.dp, Color.Black)
                            .background(if (period % 2 == 0) Color(0xFFEFEFEF) else Color.White),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
