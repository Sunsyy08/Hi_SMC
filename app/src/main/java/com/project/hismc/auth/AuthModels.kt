package com.project.hismc.auth

// 요청
data class AuthRequest(
    val grade: String,
    val classNo: String,
    val studentNo: String,
    val name: String? = null,  // 회원가입 때만 필요
    val major: String? = null, // 회원가입 때만 필요
    val password: String
)

// 응답
data class AuthResponse(
    val success: Boolean,
    val message: String? = null,
    val token: String? = null,
    val name: String? = null,
    val major: String? = null,
    val studentId: String? = null
)