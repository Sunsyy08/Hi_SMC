package com.project.hismc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.hismc.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class MealViewModel : ViewModel() {

    private val _mealInfo = MutableStateFlow<List<MealRow>>(emptyList())
    val mealInfo: StateFlow<List<MealRow>> = _mealInfo

    private val _schoolInfo = MutableStateFlow<SchoolRow?>(null)
    val schoolInfo: StateFlow<SchoolRow?> = _schoolInfo

    /**
     * 급식 정보 불러오기
     * @param date yyyyMMdd 형식
     * @param officeCode 교육청 코드, 예: "B10"
     * @param schoolCode 학교 코드, 예: "7010569"
     */
    fun loadMeal(date: String, officeCode: String, schoolCode: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMealInfo(
                    key = "2a26cb17c9684fd8b29499fabe9a412c",
                    ATPT_OFCDC_SC_CODE = officeCode,
                    SD_SCHUL_CODE = schoolCode,
                    MLSV_YMD = date,
                    Type = "json"
                )

                // null 안전하게 처리
                val rows = response.mealServiceDietInfo
                    ?.getOrNull(1)
                    ?.row ?: emptyList()
                _mealInfo.value = rows
            } catch (e: Exception) {
                Log.e("MealViewModel", "급식 로드 실패", e)
                _mealInfo.value = emptyList()
            }
        }
    }
}
