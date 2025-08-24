package com.project.hismc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.hismc.ui.theme.HismcTheme

@Composable
fun StartActivity(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = (46.sp),
            text = "Welcom HISMC",
            modifier = Modifier.padding(bottom = 60.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.smc_ms),
            contentDescription = "학교 마스코드",
            modifier = Modifier
                .size(300.dp)
                .width(400.dp)
                .height(500.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    HismcTheme{
        StartActivity()
    }
}