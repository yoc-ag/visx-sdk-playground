package com.yoc.visx.jetpackcompose.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.yoc.visx.jetpackcompose.R
import com.yoc.visx.jetpackcompose.common.Screen
import com.yoc.visx.jetpackcompose.ui.theme.colorPrimaryDark
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }

    Surface(color = colorPrimaryDark, modifier = Modifier.fillMaxSize()) {
        // Animation
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                // tween Animation
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            // Customize the delay time
            delay(2000L)
            navController.popBackStack()
            navController.navigate(Screen.HomeScreen.route)
        }

        // Image
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Change the logo
            Image(
                painter = painterResource(id = R.drawable.yoc_logo_white),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value)
            )
        }
    }
}
