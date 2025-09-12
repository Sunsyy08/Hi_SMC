package com.project.hismc.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class AuthViewModel : ViewModel() {

    var authMessage by mutableStateOf("")
        private set

    var token by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    // 회원가입
    fun signup(request: AuthRequest) {
        viewModelScope.launch {
            isLoading = true
            authMessage = ""

            try {
                Log.d("AuthViewModel", "회원가입 요청 시작: $request")

                val response = AuthRepository.api.signup(request)

                Log.d("AuthViewModel", "서버 응답 코드: ${response.code()}")
                Log.d("AuthViewModel", "서버 응답: ${response.body()}")

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        authMessage = "회원가입 완료! 학번: ${body.studentId}"
                        token = body.token // 성공 플래그로 사용
                    } else {
                        authMessage = body?.message ?: "회원가입 실패"
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AuthViewModel", "서버 에러: $errorBody")
                    authMessage = "서버 오류 (${response.code()})"
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "회원가입 네트워크 오류", e)
                authMessage = "네트워크 오류: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    // 로그인
    fun login(request: LoginRequest) {
        viewModelScope.launch {
            isLoading = true
            authMessage = ""

            try {
                Log.d("AuthViewModel", "로그인 요청: $request")

                val response = AuthRepository.api.login(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.success == true) {
                        authMessage = "로그인 성공!"
                        token = body.token
                    } else {
                        authMessage = body?.message ?: "로그인 실패"
                    }
                } else {
                    authMessage = "로그인 실패 (${response.code()})"
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "로그인 네트워크 오류", e)
                authMessage = "네트워크 오류: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    // 상태 초기화
    fun clearMessage() {
        authMessage = ""
    }
}