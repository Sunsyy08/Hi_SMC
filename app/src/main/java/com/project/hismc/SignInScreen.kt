package com.project.hismc

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
import com.project.hismc.auth.LoginRequest
import com.project.hismc.ui.theme.HismcTheme
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignInScreen(navController: NavController, major: String? = null) {
    var grade by remember { mutableStateOf("") }
    var classNo by remember { mutableStateOf("") }
    var studentNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val authViewModel: AuthViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    // 학번(studentId) 생성
    fun generateStudentId(grade: String, classNo: String, studentNo: String): String {
        val classStr = classNo.padStart(2, '0')
        val numStr = studentNo.padStart(2, '0')
        return grade + classStr + numStr
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 상단 원, 이미지, 타이틀
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .graphicsLayer(compositingStrategy = CompositingStrategy.ModulateAlpha)
            ) {
                translate(left = -80f, top = -1000f) {
                    drawCircle(Color(0xff40CEF2), radius = 350.dp.toPx())
                }
            }
            Image(
                painter = painterResource(id = R.drawable.smc_ms2),
                contentDescription = "학교 마스코드",
                modifier = Modifier.size(300.dp).offset(x = 170.dp, y = 30.dp)
            )
            Text(
                text = "Sign In",
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.offset(x = 30.dp, y = 260.dp)
            )
        }

        // ✅ 회원가입에서 온 전공 정보 표시 (있는 경우에만)
        if (!major.isNullOrBlank()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .offset(y = 380.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = "📘 선택한 학과: $major",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1565C0),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // 로그인 입력란
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = if (major.isNullOrBlank()) 500.dp else 470.dp), // 전공 카드가 있으면 위치 조정
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 학년, 반, 번호 (가로 배치)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CurvedNumberPicker(
                        label = "학년",
                        range = 1..3,
                        selectedValue = grade.toIntOrNull() ?: 1,
                        onValueChange = { grade = it.toString() },
                        modifier = Modifier.weight(1f)
                    )
                    CurvedNumberPicker(
                        label = "반",
                        range = 1..9,
                        selectedValue = classNo.toIntOrNull() ?: 1,
                        onValueChange = { classNo = it.toString() },
                        modifier = Modifier.weight(1f)
                    )
                    CurvedNumberPicker(
                        label = "번호",
                        range = 1..25,
                        selectedValue = studentNo.toIntOrNull() ?: 1,
                        onValueChange = { studentNo = it.toString() },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 비밀번호
                OutlinedTextField(
                    modifier = Modifier.height(60.dp).width(350.dp),
                    value = password,
                    onValueChange = { password = it },
                    shape = RoundedCornerShape(20.dp),
                    label = { Text("비밀번호") }
                )
            }
        }

        // 하단 원, Back 버튼
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .graphicsLayer(compositingStrategy = CompositingStrategy.ModulateAlpha)
            ) {
                rotate(degrees = -3f) {
                    translate(left = -400f, top = 1150f) {
                        drawCircle(Color(0xff40CEF2), radius = 110.dp.toPx())
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp, start = 30.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(
                    onClick = { navController.navigate(Screen.SignUp.route) },
                    shape = ButtonDefaults.shape
                ) {
                    Text(
                        text = "Back",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }

        // Start 버튼 + 서버 로그인
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = if (major.isNullOrBlank()) 600.dp else 580.dp, // 전공 카드가 있으면 위치 조정
                    start = 220.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Start",
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(12.dp))
            FloatingActionButton(
                onClick = {
                    if (grade.isBlank() || classNo.isBlank() || studentNo.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        return@FloatingActionButton
                    }

                    // ✅ 학번 생성
                    val studentId = generateStudentId(grade, classNo, studentNo)

                    // ✅ 로그인 요청 객체 (LoginRequest 사용)
                    val request = LoginRequest(
                        studentId = studentId,
                        password = password
                    )

                    coroutineScope.launch {
                        try {
                            val response = AuthRepository.api.login(request)
                            if (response.isSuccessful && response.body()?.success == true) {
                                Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()

                                // ✅ 전공 정보를 홈 화면에 전달 (회원가입에서 온 경우 해당 전공, 아니면 기본값)
                                val userMajor = major ?: "정보 없음"
                                navController.navigate(Screen.Home.createRoute(userMajor))
                            } else {
                                Toast.makeText(context, response.body()?.message ?: "로그인 실패", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "서버 오류: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    authViewModel.login(request)
                },
                shape = CircleShape,
                contentColor = Color.White,
                containerColor = Color.Black,
                modifier = Modifier.size(50.dp),
            ) {
                Icon(Icons.Filled.ArrowForward, "로그인 버튼")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSignIn() {
    HismcTheme {
        SignInScreen(navController = rememberNavController())
    }
}
