package com.project.hismc

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.hismc.data.DrawerItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserViewModel : ViewModel() {
    private val _major = MutableStateFlow<String?>(null)
    val major: StateFlow<String?> = _major.asStateFlow()

    fun setMajor(newMajor: String) {
        Log.d("UserViewModel", "setMajor 호출됨: $newMajor")
        _major.value = newMajor
        Log.d("UserViewModel", "현재 major 값: ${_major.value}")
    }

    fun getMajor(): String? {
        val currentMajor = _major.value
        Log.d("UserViewModel", "getMajor 호출됨: $currentMajor")
        return currentMajor
    }

    init {
        Log.d("UserViewModel", "UserViewModel 초기화됨")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("UserViewModel", "UserViewModel 해제됨")
    }
}

// 홈 화면
@Composable
fun HomeScreen(navController: NavController, userViewModel: UserViewModel) {
    val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 1)

    val mealViewModel: MealViewModel = viewModel()
    val meals by mealViewModel.mealInfo.collectAsState()
    val school by mealViewModel.schoolInfo.collectAsState()
    val major by userViewModel.major.collectAsState()

    val primaryBlue = Color(0xFF1E3A8A)
    val accentBlue = Color(0xFF3B82F6)
    val lightBlue = Color(0xFFDBEAFE)
    val textDark = Color(0xFF1F2937)

    val todayDate = LocalDate.now()
    val dates = listOf(todayDate.minusDays(1), todayDate, todayDate.plusDays(1))

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

    NavDrawer(navController = navController, schoolName = school?.SCHUL_NM ?: "학교 정보 로딩 중...") {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(lightBlue, Color.White, lightBlue.copy(alpha = 0.3f))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🍽️ 급식 메뉴
                Text(
                    text = "🍽️ 급식 메뉴",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryBlue,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // 급식 카드
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        val date = dates[page]
                        val formattedDate = date.format(dateFormatter)

                        val mealsForDate = meals.filter { it.MLSV_YMD == date.toString().replace("-", "") }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // 날짜 헤더
                            Text(
                                text = "$formattedDate (${when (page) {
                                    0 -> "어제"
                                    1 -> "오늘"
                                    2 -> "내일"
                                    else -> ""
                                }})",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryBlue,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            // 메뉴 리스트
                            if (mealsForDate.isEmpty()) {
                                Text("급식 정보가 없습니다", color = Color.Gray)
                            } else {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    mealsForDate.forEach { meal ->
                                        val cleanedMenu = meal.DDISH_NM
                                            ?.replace("<br/>", "\n")
                                            ?.replace(Regex("\\([^)]*\\)"), "")
                                            ?.trim()
                                            ?.split("\n")

                                        cleanedMenu?.forEach { menuItem ->
                                            if (menuItem.isNotBlank()) {
                                                Card(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    shape = RoundedCornerShape(8.dp),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = lightBlue.copy(alpha = 0.2f)
                                                    )
                                                ) {
                                                    Text(
                                                        text = menuItem.trim(),
                                                        fontSize = 14.sp,
                                                        textAlign = TextAlign.Center,
                                                        color = textDark,
                                                        modifier = Modifier.padding(6.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 페이지 인디케이터
                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index) accentBlue
                                    else Color.Gray.copy(alpha = 0.3f)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🔹 학과 카드 추가
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📘 나의 학과: $major",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryBlue
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(
    navController: NavController,
    schoolName: String,
    content: @Composable () -> Unit
) {
    val drawerItem = listOf(
        DrawerItems(Icons.Default.Home, "Home", 0, false, Screen.Home.route),
        DrawerItems(Icons.Default.Person, "Profile", 0, false, Screen.Profile.route),
        DrawerItems(Icons.Default.DateRange, "시간표", 0, false, Screen.Timetable.route)
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                            selected = currentRoute == item.route,
                            onClick = {
                                scope.launch { drawerState.close() }
                                if (currentRoute != item.route) {
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
                        title = {
                            Text(
                                text = schoolName,
                                fontWeight = FontWeight.Bold
                            )
                        },
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
