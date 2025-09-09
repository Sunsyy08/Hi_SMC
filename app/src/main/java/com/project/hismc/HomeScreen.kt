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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                    .height(250.dp),
//                    .wrapContentHeight(), // ✅ 높이 자동 조절
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) { page ->
                    val date = dates[page]
                    val formattedDate = date.format(dateFormatter)
                    val mealsForDate = meals.filter { it.MLSV_YMD == date.toString().replace("-", "") }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = formattedDate,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Box(modifier = Modifier.weight(1f)){
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (mealsForDate.isEmpty()) {
                                    Text(
                                        text = "급식 정보가 없습니다.",
                                        fontSize = 16.sp,
                                        color = Color.Gray
                                    )
                                } else {
                                    mealsForDate.forEach { meal ->
                                        // ✅ 영양 정보 제거 (괄호 안 숫자 삭제)
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    TimetableScreen("df0ad9860d1c49618a5f8de265a5c621")
//                    Text("추가 기능 자리", fontSize = 18.sp, color = Color.Gray)
                }
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
//        DrawerItems(Icons.Default.Settings, "Setting", 0, false, Screen.Setting.route)
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
