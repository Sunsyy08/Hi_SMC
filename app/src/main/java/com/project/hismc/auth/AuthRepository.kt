package com.project.hismc.auth

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRepository {

    private const val BASE_URL = "http://10.0.2.2:3000/"

    val api: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}
