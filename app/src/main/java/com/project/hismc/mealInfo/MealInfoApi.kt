package com.project.hismc.mealInfo

import retrofit2.http.GET
import retrofit2.http.Query

interface MealInfoApi {

    @GET("mealServiceDietInfo")
    suspend fun getMealInfo(
        @Query("KEY") key: String = "발급받은키",
        @Query("Type") type: String = "json",
        @Query("ATPT_OFCDC_SC_CODE") officeCode: String,
        @Query("SD_SCHUL_CODE") schoolCode: String,
        @Query("MLSV_YMD") date: String
    ): MealResponse


    @GET("schoolInfo")
    suspend fun getSchoolInfo(
        @Query("KEY") key: String,
        @Query("ATPT_OFCDC_SC_CODE") officeCode: String,
        @Query("SD_SCHUL_CODE") schoolCode: String,
        @Query("Type") type: String = "json"
    ): SchoolResponse
}
