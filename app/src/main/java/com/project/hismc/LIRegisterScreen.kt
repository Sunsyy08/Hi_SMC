package com.project.hismc

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.hismc.ui.theme.HismcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LostItemRegisterScreen(
    onClick: () -> Unit = {},
    navController: NavController
) {
    val scrollState = rememberScrollState()

    var category by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var time by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var studentId by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "분실물 등록",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.LostItems.route) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = Color(0xFF111827)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF111827)
                )
            )
        },
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { /* 등록 처리 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B82F6))
                ) {
                    Text("등록하기", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = Color(0xFFF4F5F7) // Scaffold 배경 (Material3)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 사진 추가 박스
            Text(text = "사진 추가", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .border(BorderStroke(1.dp, Color(0xFFCBD5E1)), shape = RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .clickable { /* 이미지 업로드 */ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "사진 추가",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("사진 추가", fontWeight = FontWeight.Bold)
                    Text("습득물의 사진을 추가해주세요.", color = Color(0xFF9CA3AF), fontSize = 12.sp)
                }
            }

            // 분실물 종류
            RequiredLabel(label = "습득물 종류")
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                placeholder = { Text("예: 지갑, 휴대폰, 가방 등") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFCBD5E1),
                    focusedBorderColor = Color(0xFF3B82F6)
                )
            )

            // 습득 장소
            RequiredLabel(label = "습득 장소")
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                placeholder = { Text("예: 강당 앞, 운동장 근처 등") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFCBD5E1),
                    focusedBorderColor = Color(0xFF3B82F6)
                )
            )

            // 습득 시간
            RequiredLabel(label = "습득 시간")
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                placeholder = { Text("예: 2025/10/27 14:30") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFCBD5E1),
                    focusedBorderColor = Color(0xFF3B82F6)
                )
            )

            // 상세 설명
            RequiredLabel(label = "상세 설명")
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("분실물의 색상, 브랜드, 특징 등을 입력해주세요.") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFCBD5E1),
                    focusedBorderColor = Color(0xFF3B82F6)
                ),
                maxLines = 6
            )

            // 학번
            RequiredLabel(label = "학번")
            OutlinedTextField(
                value = studentId,
                onValueChange = { studentId = it },
                placeholder = { Text("학번을 입력해주세요.") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFCBD5E1),
                    focusedBorderColor = Color(0xFF3B82F6)
                )
            )

            // 이름
            RequiredLabel(label = "이름")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("이름을 입력해주세요.") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFCBD5E1),
                    focusedBorderColor = Color(0xFF3B82F6)
                )
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun RequiredLabel(label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF111827))
        Spacer(modifier = Modifier.width(4.dp))
        Text("*", color = Color.Red, fontWeight = FontWeight.Bold)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LostItemRegisterPreview() {
//    HismcTheme {
//        LostItemRegisterScreen( navController = rememberNavController())
//    }
//}
