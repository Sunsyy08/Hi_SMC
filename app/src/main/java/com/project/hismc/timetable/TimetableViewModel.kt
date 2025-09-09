package com.project.hismc.timetable

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.launch
import java.time.DayOfWeek


class TimetableViewModel : ViewModel() {

    // 1. 1주일표 Map용
    private val _timetable = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    val timetable: StateFlow<Map<String, List<String>>> = _timetable

    // 2. 원본 row 리스트용 → Compose에서 바로 사용 가능
    private val _rows = MutableStateFlow<List<TimetableRow>>(emptyList())
    val rows: StateFlow<List<TimetableRow>> = _rows

    fun loadTimetable(apiKey: String) {
        viewModelScope.launch {
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                val today = LocalDate.now()
                val monday = today.with(DayOfWeek.MONDAY)
                val friday = monday.plusDays(4)

                val response = TimetableRetrofit.api.getTimetable(
                    key = apiKey,
                    fromDate = monday.format(formatter),
                    toDate = friday.format(formatter)
                )

                val rows = response.hisTimetable?.getOrNull(1)?.row ?: emptyList()

                // Compose용 원본 rows
                _rows.value = rows

                // 1주일표 Map
                val weekMap = rows.groupBy { it.ALL_TI_YMD ?: "" }
                    .mapValues { entry ->
                        entry.value.sortedBy { it.PERIO?.toIntOrNull() ?: 0 }
                            .map { it.ITRT_CNTNT ?: "" }
                    }
                _timetable.value = weekMap

            } catch (e: Exception) {
                e.printStackTrace()
                _timetable.value = emptyMap()
                _rows.value = emptyList()
            }
        }
    }
}
