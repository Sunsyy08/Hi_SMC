package com.project.hismc.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MealInfoApi {

    @GET("/mealServiceDietInfo")
    suspend fun getMealInfo(
        @Query("KEY") key : String,
        @Query("Type") type : String = "json",
        @Query("pIndex") pIndex : Int = 1,
        @Query("pSize") pSize : Int = 10,
        @Query("ATPT_OFCDC_SC_CODE") officeCode : String = "j10",
        @Query("SD_SCHUL_CODE") schoolCode : String = "7530167",
        @Query("MLSV_YMD") date : String
    ): MealResponse
}