package com.project.hismc.auth

// 회원가입용 요청 (서버에서 받는 필드와 정확히 맞춤)
data class AuthRequest(
    val grade: String,
    val classNo: String,
    val studentNo: String,
    val name: String,
    val major: String,
    val password: String
)

// 로그인용 요청
data class LoginRequest(
    val studentId: String,
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