package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.project.hismc.ui.theme.HismcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindLostItemScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("분실물", "습득물")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("분실물 센터", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* 뒤로가기 */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            Button(
                onClick = { /* 분실물/습득물 신고 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2F80ED)
                )
            ) {
                Text("+ 분실물/습득물 신고", color = Color.White, fontSize = 16.sp)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF2F2F2)) // 🔹 회색 배경
        ) {
            // 탭
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == index) Color(0xFF2F80ED) else Color.Gray,
                        modifier = Modifier
                            .clickable { selectedTab = index }
                            .padding(vertical = 8.dp)
                    )
                }
            }

            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // 내 신고 내역
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White), // 🔹 카드 흰색
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("내 신고 내역", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(
                                "내가 신고한 분실물/습득물을 확인하세요.",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                // 분실물 카드 리스트
                items(3) { index ->
                    FindLostItemCard(
                        title = when (index) {
                            0 -> "갈색 가죽 지갑"
                            1 -> "에어팟 프로 케이스"
                            else -> "검은색 백팩"
                        },
                        location = when (index) {
                            0 -> "인문관 302호"
                            1 -> "학생회관 1층 라운지"
                            else -> "중앙도서관 2층 열람실"
                        },
                        statusText = if (index == 0) "분실" else "보관중"
                    )
                }
            }
        }
    }
}

@Composable
fun FindLostItemCard(title: String, location: String, statusText: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), // 🔹 카드 흰색
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 이미지 자리 (빈 상태)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0E0E0)) // 연회색 placeholder
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = statusText,
                        color = if (statusText == "분실") Color(0xFFFF6F61) else Color(0xFFFFA726),
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(
                                if (statusText == "분실") Color(0xFFFFEBEE) else Color(0xFFFFF3E0),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                }
                Text(location, color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}

@Preview
@Composable
fun FindLostItemPreview() {
    HismcTheme {
        FindLostItemScreen()
    }
}