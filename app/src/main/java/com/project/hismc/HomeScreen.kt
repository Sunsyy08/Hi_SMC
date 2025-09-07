package com.project.hismc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.hismc.data.DrawerItems
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val images = listOf(
        R.drawable.smc_ms2,
        R.drawable.smc_ms
    )
    val pagerState = rememberPagerState(pageCount = { images.size })

    NavDrawer(navController = navController) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 이미지 캐러셀
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
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(12.dp))
                            .height(240.dp)
                            .width(330.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = images[currentPage]),
                            contentDescription = ""
                        )
                    }
                }
            }

            // 아래 컨텐츠 박스
            Box(
                modifier = Modifier
                    .padding(top = 380.dp, start = 50.dp)
                    .width(340.dp)
                    .height(300.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ),
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
        DrawerItems(Icons.Default.Face, "Profile", 0, false, Screen.SignUp.route),
        DrawerItems(Icons.Default.Email, "Inbox", 32, true, Screen.SignUp.route),
        DrawerItems(Icons.Default.Favorite, "Favorite", 5, true, Screen.SignUp.route),
        DrawerItems(Icons.Default.Settings, "Setting", 0, false, Screen.SignUp.route)
    )

    var selectedItem by remember { mutableStateOf(drawerItem[0]) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Drawer 상단 헤더
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(color = Color(0xff40CEF2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.wrapContentSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.smc_ms),
                                contentDescription = "학교 마스코드",
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = "Mr Park",
                                modifier = Modifier.padding(top = 16.dp),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        Divider(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            thickness = 1.dp,
                            color = Color.DarkGray
                        )
                    }

                    // 메뉴 아이템 리스트
                    drawerItem.forEach { item ->
                        NavigationDrawerItem(
                            label = { Text(text = item.text) },
                            selected = item == selectedItem,
                            onClick = {
                                selectedItem = item
                                scope.launch { drawerState.close() } // 닫기
                                navController.navigate(item.page) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier.padding(horizontal = 20.dp),
                            icon = { Icon(imageVector = item.icon, contentDescription = item.text) },
                            badge = {
                                if (item.hasBadge) {
                                    Badge {
                                        Text(text = item.badgeCount.toString(), fontSize = 12.sp)
                                    }
                                }
                            }
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
