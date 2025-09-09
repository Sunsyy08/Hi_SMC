package com.project.hismc.timetable

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TimetableApi {
    @GET("hub/hisTimetable")
    suspend fun getTimetable(
        @Query("KEY") key: String,
        @Query("Type") type: String = "json",
        @Query("ATPT_OFCDC_SC_CODE") atptCode: String = "B10",
        @Query("SD_SCHUL_CODE") schoolCode: String = "7010537",
        @Query("AY") year: String = "2025",
        @Query("SEM") semester: String = "2",
        @Query("GRADE") grade: String = "2",
        @Query("CLASS_NM") classNm: String = "6",
        @Query("TI_FROM_YMD") fromDate: String,
        @Query("TI_TO_YMD") toDate: String
    ): TimetableResponse
}



object TimetableRetrofit {
    val api: TimetableApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://open.neis.go.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TimetableApi::class.java)
    }
}