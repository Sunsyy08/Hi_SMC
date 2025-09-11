package com.project.hismc.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class AuthViewModel : ViewModel() {

    var authMessage by mutableStateOf("")
        private set

    var token by mutableStateOf<String?>(null)
        private set

    fun signup(request: AuthRequest) {
        AuthRepository.api.signup(request).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val body = response.body()
                authMessage = body?.message ?: "서버 오류"
                if (body?.success == true) token = body.token
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                authMessage = t.message ?: "네트워크 오류"
            }
        })
    }

    fun login(request: AuthRequest) {
        AuthRepository.api.login(request).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val body = response.body()
                authMessage = body?.message ?: "서버 오류"
                if (body?.success == true) token = body.token
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                authMessage = t.message ?: "네트워크 오류"
            }
        })
    }
}
