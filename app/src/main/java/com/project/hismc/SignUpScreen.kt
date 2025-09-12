package com.project.hismc

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.hismc.auth.AuthRequest
import com.project.hismc.auth.AuthRepository
import com.project.hismc.auth.AuthResponse
import com.project.hismc.auth.AuthViewModel
import com.project.hismc.ui.theme.HismcTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var classNo by remember { mutableStateOf("") }
    var studentNo by remember { mutableStateOf("") }
    var major by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    val sharedPref = context.getSharedPreferences("MyAppPref", Context.MODE_PRIVATE)
    val coroutineScope = rememberCoroutineScope()

    // studentId 생성 함수
    fun generateStudentId(grade: String, classNo: String, studentNo: String): String {
        val classStr = classNo.padStart(2, '0')
        val numStr = studentNo.padStart(2, '0')
        return grade + classStr + numStr
    }

    // 회원가입 함수
    fun performSignUp() {
        if (name.isBlank() || grade.isBlank() || classNo.isBlank() ||
            studentNo.isBlank() || major.isBlank() || password.isBlank()
        ) {
            Toast.makeText(context, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val request = AuthRequest(
            grade = grade,
            classNo = classNo,
            studentNo = studentNo,
            name = name,
            major = major,
            password = password
        )

        coroutineScope.launch {
            try {
                val response = AuthRepository.api.signup(request)
                Log.d("AuthDebug", "HTTP 코드: ${response.code()}")
                Log.d("AuthDebug", "응답 바디: ${response.body()}")

                val res = response.body()
                if (response.isSuccessful && res != null && res.success) {
                    sharedPref.edit().putBoolean("isSignedUp", true).apply()
                    Toast.makeText(
                        context,
                        "회원가입 완료! 이제 Sign In 버튼을 눌러주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        res?.message ?: "회원가입 실패",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("AuthDebug", "서버 오류: ${e.message}", e)
                Toast.makeText(context, "서버 오류: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.signup(request)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 상단 배경 원
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .graphicsLayer(compositingStrategy = CompositingStrategy.ModulateAlpha)
        ) {
            translate(left = -80f, top = -1200f) {
                drawCircle(Color(0xff40CEF2), radius = 350.dp.toPx())
            }
        }

        Image(
            painter = painterResource(id = R.drawable.smc_ms),
            contentDescription = "학교 마스코트",
            modifier = Modifier
                .size(200.dp)
                .offset(x = 210.dp, y = 30.dp)
        )

        Text(
            "Create \nAccount",
            modifier = Modifier.offset(x = 30.dp, y = 150.dp),
            fontSize = 50.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 50.sp,
            color = Color.White
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            OutlinedTextField(
                modifier = Modifier.height(60.dp).width(350.dp),
                value = name,
                onValueChange = { name = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text("이름") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.height(60.dp).width(350.dp),
                value = grade,
                onValueChange = { grade = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text("학년") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.height(60.dp).width(350.dp),
                value = classNo,
                onValueChange = { classNo = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text("반") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.height(60.dp).width(350.dp),
                value = studentNo,
                onValueChange = { studentNo = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text("번호") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.height(60.dp).width(350.dp),
                value = major,
                onValueChange = { major = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text("학과") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.height(60.dp).width(350.dp),
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text("비밀번호") }
            )

            Text(
                text = "Sign Up",
                fontSize = 26.sp,
                modifier = Modifier.offset(x = 30.dp, y = 130.dp),
                fontWeight = FontWeight.Medium
            )

            FloatingActionButton(
                onClick = { performSignUp() },
                shape = CircleShape,
                contentColor = Color.White,
                containerColor = Color.Black,
                modifier = Modifier
                    .offset(x = 120.dp, y = 90.dp)
                    .size(50.dp),
            ) {
                Icon(Icons.Filled.ArrowForward, "회원가입 버튼")
            }
        }

        // 하단 배경 원
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .graphicsLayer(compositingStrategy = CompositingStrategy.ModulateAlpha)
        ) {
            rotate(degrees = -3f) {
                translate(left = 280f, top = 1200f) {
                    drawCircle(Color(0xff40CEF2), radius = 110.dp.toPx())
                }
            }
        }

        // Sign In 버튼 (보조용)
        TextButton(
            onClick = {
                if (name.isNotBlank() && grade.isNotBlank() && classNo.isNotBlank() &&
                    studentNo.isNotBlank() && major.isNotBlank() && password.isNotBlank()
                ) {
                    navController.navigate(Screen.SignIn.route)
                } else {
                    Toast.makeText(
                        context,
                        "모든 정보를 입력하고 회원가입을 완료해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            shape = ButtonDefaults.shape,
            modifier = Modifier.offset(x = 270.dp, y = 840.dp).zIndex(1f)
        ) {
            Text(
                text = "Sign In",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {
    HismcTheme {
        SignUpScreen(navController = rememberNavController())
    }
}