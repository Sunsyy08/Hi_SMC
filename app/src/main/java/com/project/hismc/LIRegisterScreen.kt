package com.project.hismc

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.project.hismc.ui.theme.HismcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoundItemRegisterScreen(
    onBackClick: () -> Unit = {}
) {
    var itemName by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var time by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var contact by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "습득물 등록",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF1F2937)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = Color(0xFF1F2937)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF4F5F7)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //  사진 추가 박스
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .border(
                        BorderStroke(1.dp, Color(0xFFCBD5E1)),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .clickable { /* 이미지 업로드 로직 */ },
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
                    Text("사진 추가", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "습득물의 사진을 추가해주세요.",
                        fontSize = 12.sp,
                        color = Color(0xFF9CA3AF)
                    )
                }
            }

            //  습득물 종류
            OutlinedTextField(
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("습득물 종류") },
                placeholder = { Text("예: 지갑, 휴대폰, 가방 등") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                )
            )

            //  습득 장소
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("습득 장소") },
                placeholder = { Text("자세한 위치를 입력해주세요.") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                )
            )

            //  습득 시간
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("습득 시간") },
                placeholder = { Text("예: 2025-10-27 14:30") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                )
            )

            //  상세 설명
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("상세 설명") },
                placeholder = { Text("특징이나 구체적인 정보를 입력해주세요.") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                )
            )

            //  연락처
            OutlinedTextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text("연락처") },
                placeholder = { Text("연락받으실 번호를 입력해주세요.") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = Color(0xFFCBD5E1)
                )
            )

            // 등록 버튼
            Button(
                onClick = { /* 등록 처리 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6)
                )
            ) {
                Text("등록하기", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoundItemRegisterScreenPreview() {
    HismcTheme {
        FoundItemRegisterScreen()
    }
}
