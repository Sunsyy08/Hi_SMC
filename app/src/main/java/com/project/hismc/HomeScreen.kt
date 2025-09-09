package com.project.hismc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun HomeScreen(navController: NavController) {
    val images = listOf(
        R.drawable.smc_ms2,
        R.drawable.smc_ms
    )
    val pagerState = rememberPagerState(pageCount = { images.size })

    //  ViewModel 가져오기
    val mealViewModel: MealViewModel = viewModel()
    val meals by mealViewModel.mealInfo.collectAsState()
    val school by mealViewModel.schoolInfo.collectAsState()

    // 오늘 날짜 (yyyyMMdd)
    val today = remember { java.time.LocalDate.now().toString().replace("-", "") }

    //  처음 화면 들어올 때 데이터 로드
    LaunchedEffect(Unit) {
        mealViewModel.loadMeal(
            date = today,
            officeCode = "B10",     // 서울특별시교육청 (학교에 맞게 바꿔야 함)
            schoolCode = "7010537", // 세명컴퓨터고등학교 코드 (네 학교 코드 넣어야 함)
            mealCode = "2"          // 1=조식, 2=중식, 3=석식
        )
        mealViewModel.loadSchoolInfo("B10", "7010537")
    }

    NavDrawer(navController = navController) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(top = 50.dp, start = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 학교 정보
                school?.let {
                    Text(
                        text = "학교명: ${it.SCHUL_NM ?: "정보 없음"}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
//                Text(text = "주소: ${it.ORG_RDNMA ?: "정보 없음"}")
//                Text(text = "전화: ${it.ORG_TELNO ?: "정보 없음"}")
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            //  상단 이미지 캐러셀
            Box(
                modifier = Modifier
                    .padding(top = 100.dp, start = 50.dp)
                    .width(340.dp)
                    .height(250.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .padding(8.dp),
            ) {
                HorizontalPager(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    state = pagerState,
                ) { currentPage ->
                    Column {

                        // 급식 정보
                        if (meals.isEmpty()) {
                            Text(text = "오늘 급식 정보가 없습니다.")
                        } else {
                            meals.forEach { meal ->
                                Text(text = "날짜: ${meal.MLSV_YMD ?: ""}")
                                Text(
                                    text = meal.DDISH_NM?.replace("<br/>", "\n") ?: "",
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }

            //  아래쪽 급식 정보 박스
            Box(
                modifier = Modifier
                    .padding(top = 380.dp, start = 50.dp)
                    .width(340.dp)
                    .height(300.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
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
