package com.project.hismc.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MealInfoApi {

    @GET("mealServiceDietInfo")
    suspend fun getMealInfo(
        @Query("KEY") key: String,
        @Query("ATPT_OFCDC_SC_CODE") officeCode: String,
        @Query("SD_SCHUL_CODE") schoolCode: String,
        @Query("MMEAL_SC_CODE") mealCode: String,
        @Query("MLSV_YMD") date: String,
        @Query("Type") type: String = "json"
    ): MealResponse

    @GET("schoolInfo")
    suspend fun getSchoolInfo(
        @Query("KEY") key: String,
        @Query("ATPT_OFCDC_SC_CODE") officeCode: String,
        @Query("SD_SCHUL_CODE") schoolCode: String,
        @Query("Type") type: String = "json"
    ): SchoolResponse
}
