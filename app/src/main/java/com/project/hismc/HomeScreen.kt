package com.project.hismc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.hismc.data.DrawerItems
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 1)

    val mealViewModel: MealViewModel = viewModel()
    val meals by mealViewModel.mealInfo.collectAsState()
    val school by mealViewModel.schoolInfo.collectAsState()

    val todayDate = LocalDate.now()
    val dates = listOf(
        todayDate.minusDays(1),
        todayDate,
        todayDate.plusDays(1)
    )

    LaunchedEffect(Unit) {
        dates.forEach { date ->
            mealViewModel.loadMeal(
                date = date.toString().replace("-", ""),
                officeCode = "B10",
                schoolCode = "7010537",
                mealCode = "2"
            )
        }
        mealViewModel.loadSchoolInfo("B10", "7010537")
    }

    val dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일")

    NavDrawer(navController = navController) {
        Box(modifier = Modifier.fillMaxSize()) {

            // 학교 정보
            Row(
                modifier = Modifier
                    .padding(top = 50.dp, start = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                school?.let {
                    Text(
                        text = "학교명: ${it.SCHUL_NM ?: "정보 없음"}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // 상단 급식 카드
            Card(
                modifier = Modifier
                    .padding(top = 100.dp, start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(Alignment.TopCenter),
//                    .wrapContentHeight(), // ✅ 높이 자동 조절
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp) // ✅ 카드 높이 고정
                ) { page ->
                    val date = dates[page]
                    val formattedDate = date.format(dateFormatter)

                    // 오늘 / 어제 / 내일 표시
                    val label = when (page) {
                        0 -> "어제"
                        1 -> "오늘"
                        2 -> "내일"
                        else -> ""
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // 날짜 + 라벨
                            Text(
                                buildAnnotatedString {
                                    append(formattedDate)
                                    append("  ")
                                    withStyle(SpanStyle(color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)) {
                                        append(label)
                                    }
                                },
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            val mealsForDate = meals.filter { it.MLSV_YMD == date.toString().replace("-", "") }

                            Box(
                                modifier = Modifier
                                    .weight(1f) // ✅ 내용 부족 시에도 카드 높이 유지
                                    .fillMaxWidth()
                            ) {
                                if (mealsForDate.isEmpty()) {
                                    Text(
                                        text = "급식 정보가 없습니다.",
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                } else {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .verticalScroll(rememberScrollState()),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        mealsForDate.forEach { meal ->
                                            val cleanedMenu = meal.DDISH_NM
                                                ?.replace("<br/>", "\n")
                                                ?.replace(Regex("\\([^)]*\\)"), "")
                                                ?.trim()
                                            Text(
                                                text = cleanedMenu ?: "",
                                                fontSize = 16.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 아래쪽 카드
            Card(
                modifier = Modifier
                    .padding(top = 400.dp, start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val drawerItem = listOf(
        DrawerItems(Icons.Default.Home, "Home", 0, false, Screen.Home.route),
        DrawerItems(Icons.Default.Person, "Profile", 0, false, Screen.Profile.route),
        DrawerItems(Icons.Default.DateRange, "시간표", 0, false, Screen.Timetable.route)
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //  NavController 상태 감지
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(color = Color(0xff40CEF2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.wrapContentSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.smc_ms),
                                contentDescription = "학교 마스코드",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = "Mr Park",
                                modifier = Modifier.padding(top = 12.dp),
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }

                    drawerItem.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(text = item.text) },
                            selected = currentRoute == item.route, // 현재 경로랑 비교해서 선택 상태 표시
                            onClick = {
                                scope.launch { drawerState.close() }
                                if (currentRoute != item.route) { // 같은 화면 중복 이동 방지
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            modifier = Modifier.padding(horizontal = 20.dp),
                            icon = { Icon(imageVector = item.icon, contentDescription = item.text) }
                        )
                    }
                }
            }
        },
        drawerState = drawerState,
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "HI! SMC") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "menu Icon"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    content()
                }
            }
        }
    )
}
