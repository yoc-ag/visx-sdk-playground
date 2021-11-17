package com.yoc.visx.jetpackcompose.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yoc.visx.jetpackcompose.common.Screen
import com.yoc.visx.jetpackcompose.ui.theme.Demo_showcase_jetpack_composeTheme
import com.yoc.visx.jetpackcompose.ui.theme.colorPrimaryDark
import com.yoc.visx.jetpackcompose.ui.theme.whiteSmoke

@Composable
fun HomeScreen(navController: NavController) {
    Demo_showcase_jetpack_composeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorPrimaryDark)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to YOC Jetpack Compose Demo Showcase",
                    color = whiteSmoke,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(40.dp))
                Button(onClick = {
                    navController.navigate(Screen.BannerScreen.route)
                }) {
                    TextOnButton(text = "VIS.X Banner Showcase")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    navController.navigate(Screen.InterstitialScreen.route)
                }) {
                    TextOnButton(text = "VIS.X Interstitial Showcase")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    navController.navigate(Screen.UniversalScreen.route)
                }) {
                    TextOnButton(text = "VIS.X Universal Showcase")
                }
            }
        }
    }
}

@Composable
fun TextOnButton(text: String) {
    Text(
        text = text,
        Modifier.width(250.dp),
        textAlign = TextAlign.Center
    )
}
