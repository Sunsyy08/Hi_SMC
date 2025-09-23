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
import com.project.hismc.auth.AuthViewModel
import com.project.hismc.ui.theme.HismcTheme

// SignUpScreen.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController, userViewModel: UserViewModel) {
    var name by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var classNo by remember { mutableStateOf("") }
    var studentNo by remember { mutableStateOf("") }
    var major by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    // 학과 선택 리스트
    val majors = listOf("스마트 보안솔루션과", "모빌리티메이커과", "인공지능소프트웨어과", "게임소프트웨어과")
    var expanded by remember { mutableStateOf(false) }

    // ✅ 디버깅: UserViewModel 상태 확인
    LaunchedEffect(Unit) {
        Log.d("SignUpScreen", "SignUpScreen 시작 - UserViewModel: $userViewModel")
    }

    // 회원가입 함수
    fun performSignUp() {
        if (name.isBlank() || grade.isBlank() || classNo.isBlank() || studentNo.isBlank() || major.isBlank() || password.isBlank()) {
            Toast.makeText(context, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("SignUpScreen", "회원가입 시도 - 선택된 학과: $major")

        val request = AuthRequest(
            grade = grade.trim(),
            classNo = classNo.trim(),
            studentNo = studentNo.trim(),
            name = name.trim(),
            major = major.trim(),
            password = password.trim()
        )

        // ✅ 중요: UserViewModel에 학과 저장
        userViewModel.setMajor(major)

        // 저장 후 확인
        Log.d("SignUpScreen", "학과 저장 후 확인: ${userViewModel.getMajor()}")

        // 서버 요청
        authViewModel.signup(request)

        Toast.makeText(context, "회원가입 완료! 로그인해주세요.", Toast.LENGTH_SHORT).show()

        // 로그인 화면으로 이동
        navController.navigate(Screen.SignIn.route)
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
                .statusBarsPadding()
                .padding(bottom = 120.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 이름
            OutlinedTextField(
                modifier = Modifier.height(60.dp).width(350.dp),
                value = name,
                onValueChange = { name = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text("이름") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            // 학년, 반, 번호
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

            // 학과 선택
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.width(350.dp).height(60.dp)
            ) {
                OutlinedTextField(
                    value = major,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("학과") },
                    shape = RoundedCornerShape(10.dp),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    majors.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                major = option
                                expanded = false
                                Log.d("SignUpScreen", "학과 선택됨: $option")
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            // 비밀번호
            OutlinedTextField(
                modifier = Modifier.height(60.dp).width(350.dp),
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(10.dp),
                label = { Text("비밀번호") }
            )
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

        // Sign Up 버튼
        TextButton(
            onClick = { performSignUp() },
            shape = ButtonDefaults.shape,
            modifier = Modifier.offset(x = 270.dp, y = 840.dp).zIndex(1f)
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}