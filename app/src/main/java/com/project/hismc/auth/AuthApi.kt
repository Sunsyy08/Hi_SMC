package com.project.hismc.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/signup")
    fun signup(@Body request: AuthRequest): Call<AuthResponse>

    @POST("api/auth/login")
    fun login(@Body request: AuthRequest): Call<AuthResponse>
}
