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

    fun loadMeal(date: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMealInfo(
                    key = "2a26cb17c9684fd8b29499fabe9a412c",
                    date = date
                )
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
