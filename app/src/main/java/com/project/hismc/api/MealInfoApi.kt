package com.project.hismc.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MealInfoApi {

    @GET("mealServiceDietInfo")
    suspend fun getMealInfo(
        @Query("KEY") key: String,
        @Query("ATPT_OFCDC_SC_CODE") ATPT_OFCDC_SC_CODE: String,
        @Query("SD_SCHUL_CODE") SD_SCHUL_CODE: String,
        @Query("MLSV_YMD") MLSV_YMD: String,
        @Query("Type") Type: String = "json"
    ): MealResponse
}
