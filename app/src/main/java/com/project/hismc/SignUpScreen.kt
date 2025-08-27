package com.project.hismc

import android.R.attr.onClick
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.hismc.ui.theme.HismcTheme
import java.time.format.TextStyle

@Composable
fun SignUpScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .graphicsLayer(
                    compositingStrategy = CompositingStrategy.ModulateAlpha
                )
        ) {
//            translate(left = -400f, top = -800f) {
//                drawCircle(Color(0xffB52FF8), radius = 200.dp.toPx())
//            }
            translate(left = -80f, top = -1200f, ) {
                drawCircle(Color(0xff40CEF2), radius = 350.dp.toPx())
                RoundedCornerShape(400.dp)
            }
        }
        Image(
            painter = painterResource(id = R.drawable.smc_ms),
            contentDescription = "학교 마스코트",
            modifier = Modifier
                .size(200.dp)
                .offset(x = (210).dp, y = (30).dp)
        )
        Text(
            "Create \nAccount",
            modifier = Modifier
                .offset(x = (30).dp, y = (150).dp),
            fontSize = (50.sp),
            letterSpacing = 2.sp,
            lineHeight = 50.sp,
            color = Color.White,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                shape = RoundedCornerShape(20.dp),
                label = { Text("인순이 전화번호를 입력하세요.") }
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun Preview1(){
    HismcTheme{
        SignUpScreen(navController = rememberNavController())
    }
}