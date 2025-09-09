package com.project.hismc.mealInfo

// 급식 응답 모델
data class MealResponse(
    val mealServiceDietInfo: List<MealServiceDietInfo>? = emptyList()
)

data class MealServiceDietInfo(
    val row: List<MealRow>? = emptyList()
)

data class MealRow(
    val ATPT_OFCDC_SC_CODE: String? = "",
    val SD_SCHUL_CODE: String? = "",
    val MMEAL_SC_CODE: String? = "",
    val MMEAL_SC_NM: String? = "",
    val MLSV_YMD: String? = "",
    val DDISH_NM: String? = "",
    val CAL_INFO: String? = "",
    val NTR_INFO: String? = ""
)

// 학교 정보 모델
data class SchoolResponse(
    val schoolInfo: List<SchoolServiceInfo>? = emptyList()
)

data class SchoolServiceInfo(
    val row: List<SchoolRow>? = emptyList()
)

data class SchoolRow(
    val SCHUL_NM: String? = "",
    val ORG_RDNMA: String? = "",
    val ORG_TELNO: String? = ""
)
