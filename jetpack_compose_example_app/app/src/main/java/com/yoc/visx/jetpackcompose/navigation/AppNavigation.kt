package com.yoc.visx.jetpackcompose.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yoc.visx.jetpackcompose.screen.home.HomeScreen
import com.yoc.visx.jetpackcompose.screen.splash.SplashScreen
import com.yoc.visx.jetpackcompose.common.Screen
import com.yoc.visx.jetpackcompose.screen.banner.BannerScreen
import com.yoc.visx.jetpackcompose.screen.interstitial.InterstitialScreen
import com.yoc.visx.jetpackcompose.screen.universal.UniversalScreen

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {

        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(Screen.BannerScreen.route) {
            BannerScreen(context, navController)
        }

        composable(Screen.InterstitialScreen.route) {
            InterstitialScreen(context, navController)
        }

        composable(Screen.UniversalScreen.route) {
            UniversalScreen(context, navController)
        }
    }
}