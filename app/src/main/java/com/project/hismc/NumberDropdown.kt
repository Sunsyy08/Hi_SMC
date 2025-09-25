package com.project.hismc

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberDropdown(
    label: String,
    options: List<String>,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(60.dp),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CurvedNumberPicker(
    label: String,
    range: IntRange,
    selectedValue: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)

        val rangeSize = range.count()
        val virtualListSize = rangeSize * 1000 // 매우 큰 가상 리스트
        val centerOffset = virtualListSize / 2

        // 초기값 1에 해당하는 가상 인덱스 계산
        val initialVirtualIndex = centerOffset
        val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialVirtualIndex - 2)

        // 스크롤할 때 중앙값 찾아서 선택값 업데이트
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                .collect { visibleItems ->
                    if (visibleItems.isNotEmpty()) {
                        val layoutInfo = listState.layoutInfo
                        val centerY = layoutInfo.viewportStartOffset + (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2
                        val closest = visibleItems.minByOrNull { item ->
                            abs((item.offset + item.size / 2) - centerY)
                        }
                        closest?.let { item ->
                            // 가상 인덱스를 실제 값으로 변환
                            val actualIndex = item.index % rangeSize
                            val value = range.first + actualIndex
                            onValueChange(value)
                        }
                    }
                }
        }

        Box(
            modifier = Modifier
                .height(150.dp)
                .width(80.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                state = listState,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(virtualListSize) { virtualIndex ->
                    // 가상 인덱스를 실제 값으로 변환 (순환)
                    val actualIndex = virtualIndex % rangeSize
                    val value = range.first + actualIndex

                    // 현재 뷰포트 중앙에 있는 아이템의 인덱스 계산
                    val layoutInfo = listState.layoutInfo
                    val visibleItems = layoutInfo.visibleItemsInfo
                    val centerY = layoutInfo.viewportStartOffset + (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

                    val centerItem = visibleItems.minByOrNull { item ->
                        abs((item.offset + item.size / 2) - centerY)
                    }
                    val centerVirtualIndex = centerItem?.index ?: virtualIndex

                    val distanceFromCenter = abs(virtualIndex - centerVirtualIndex)

                    val scale = (1f - (distanceFromCenter * 0.15f)).coerceAtLeast(0.7f)
                    val alpha = (1f - (distanceFromCenter * 0.2f)).coerceAtLeast(0.3f)
                    val rotationAngleX = (distanceFromCenter * 15f).coerceAtMost(45f)

                    Text(
                        text = value.toString(),
                        fontSize = 24.sp,
                        fontWeight = if (distanceFromCenter == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (distanceFromCenter == 0) Color.Black else Color.Gray,
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                this.alpha = alpha
                                rotationX = rotationAngleX
                            }
                            .padding(4.dp)
                    )
                }
            }

            // 선택된 숫자 위/아래 구분선
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.weight(1f))

                // 상단 구분선
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.width(60.dp)
                )

                // 중앙 선택 영역 (약 28dp 높이)
                Spacer(modifier = Modifier.height(28.dp))

                // 하단 구분선
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.width(60.dp)
                )

                Box(modifier = Modifier.weight(1f))
            }
        }
    }
}