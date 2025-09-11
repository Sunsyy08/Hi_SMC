package com.project.hismc.auth

// 요청
data class AuthRequest(
    val grade: String,       // 학년, 예: "2"
    val classNo: String,     // 반, 예: "2" → 백엔드에서 "02"로 변환 가능
    val studentNo: String,   // 번호, 예: "7" → 백엔드에서 "07"로 변환 가능
    val major: String? = null,
    val name: String? = null,
    val password: String
)

// 응답
data class AuthResponse(
    val success: Boolean,    // 성공 여부
    val message: String,     // 서버 메시지
    val studentId: String? = null,  // 성공 시 학생 ID
    val token: String? = null,      // 로그인 시 JWT
    val name: String? = null,       // 회원가입/로그인 시 이름
    val major: String? = null       // 학과
)

