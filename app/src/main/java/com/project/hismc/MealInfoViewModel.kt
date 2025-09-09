package com.project.hismc

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.hismc.mealInfo.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {

    private val _mealInfo = MutableStateFlow<List<MealRow>>(emptyList())
    val mealInfo: StateFlow<List<MealRow>> = _mealInfo

    // nullable 타입으로 초기화 (null 넣으려면 타입이 nullable 이어야 함)
    private val _schoolInfo = MutableStateFlow<SchoolRow?>(null)
    val schoolInfo: StateFlow<SchoolRow?> = _schoolInfo

    fun loadMeal(date: String, officeCode: String, schoolCode: String, mealCode: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMealInfo(
                    key = "2a26cb17c9684fd8b29499fabe9a412c",
                    officeCode = officeCode,
                    schoolCode = schoolCode,
                    date = date
                )

                val newMeals = response.mealServiceDietInfo
                    ?.getOrNull(1)
                    ?.row ?: emptyList()

                _mealInfo.value = _mealInfo.value + newMeals
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadSchoolInfo(officeCode: String, schoolCode: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getSchoolInfo(
                    key = "2a26cb17c9684fd8b29499fabe9a412c",
                    officeCode = officeCode,
                    schoolCode = schoolCode
                )

                val row = response.schoolInfo
                    ?.getOrNull(1)
                    ?.row
                    ?.getOrNull(0)

                _schoolInfo.value = row
                Log.d("MealViewModel", "학교 정보 불러오기 성공: ${row?.SCHUL_NM}")
            } catch (e: Exception) {
                Log.e("MealViewModel", "학교 정보 로드 실패", e)
                _schoolInfo.value = null
            }
        }
    }
}