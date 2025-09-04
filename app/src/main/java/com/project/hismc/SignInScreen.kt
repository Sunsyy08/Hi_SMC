package com.project.hismc

import android.R.attr.text
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.hismc.ui.theme.HismcTheme

@Composable
fun SignInScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Box(
            modifier = Modifier
            .fillMaxSize()
        ){
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .graphicsLayer(
                        compositingStrategy = CompositingStrategy.ModulateAlpha
                    )
            ) {
                translate(left = -80f, top = -1000f, ) {
                    drawCircle(Color(0xff40CEF2), radius = 350.dp.toPx())
                    RoundedCornerShape(400.dp)
                }
            }
            Image(
                painter = painterResource(id = R.drawable.smc_ms2),
                contentDescription = "학교 마스코드",
                modifier = Modifier
                    .size(300.dp)
                    .offset(x = (170.dp), y = (30.dp))
            )
            Text(
                text = "Sign In",
                fontSize = (60.sp),
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier
                    .offset(x = (30).dp, y = (260).dp),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 500.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .height(60.dp)
                        .width(350.dp),
                    value = email,
                    onValueChange = {email = it},
                    shape = RoundedCornerShape(20.dp),
                    label = {Text("email")}
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .height(60.dp)
                        .width(350.dp),
                    value = password,
                    onValueChange = {password = it},
                    shape = RoundedCornerShape(20.dp),
                    label = {Text("email")}
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .graphicsLayer(
                        compositingStrategy = CompositingStrategy.ModulateAlpha
                    )
            ) {
                rotate(degrees = -3f) {
                    translate(left = -400f, top = 1150f) {
                        drawCircle(Color(0xff40CEF2), radius = 110.dp.toPx())
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp, start = 30.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(Screen.SignUp.route)
                    },
                    shape = ButtonDefaults.shape
                ) {
                    Text(
                        text = "Back",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 600.dp, start = 220.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Start",
                fontSize = 30.sp,
                modifier = Modifier,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(12.dp))
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Home.route)
                },
                shape = CircleShape,
                contentColor = Color(0xffffffff),
                containerColor = Color(0xff000000),
                modifier = Modifier
                    .size(50.dp),
            ) {
                Icon(Icons.Filled.ArrowForward, "Large floating action button")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    HismcTheme { 
        SignInScreen(navController = rememberNavController())
    }
}