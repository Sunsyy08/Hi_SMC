package com.project.hismc.auth

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

    // 회원가입
    fun signup(request: AuthRequest) {
        viewModelScope.launch {
            try {
                val response = AuthRepository.api.signup(request) // suspend 함수
                val body = response.body()
                authMessage = body?.message ?: "서버 오류"
                if (body?.success == true) token = body.token
            } catch (e: Exception) {
                authMessage = e.message ?: "네트워크 오류"
            }
        }
    }

    //로그인
    fun login(request: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = AuthRepository.api.login(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    authMessage = body?.message ?: "서버 오류"
                    if (body?.success == true) {
                        token = body.token
                    }
                } else {
                    authMessage = "로그인 실패 (${response.code()})"
                }
            } catch (e: Exception) {
                authMessage = e.message ?: "네트워크 오류"
            }
        }
    }

}
