package com.project.hismc.api

data class MealResponse(
    val mealServiceDietInfo: List<MealServiceDietInfo>? = emptyList()
)

data class MealServiceDietInfo(
    val row: List<MealRow>? = emptyList()
)

data class MealRow(
    val MLSV_YMD: String? = "",
    val DDISH_NM: String? = ""
)

data class SchoolResponse(
    val schoolInfo: List<SchoolInfo>? = emptyList()
)

data class SchoolInfo(
    val row: List<SchoolRow>? = emptyList()
)

data class SchoolRow(
    val SCHUL_NM: String? = "",
    val ORG_RDNMA: String? = "",
    val ORG_TELNO: String? = ""
)
