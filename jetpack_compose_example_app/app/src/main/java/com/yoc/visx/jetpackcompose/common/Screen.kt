package com.yoc.visx.jetpackcompose.common

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object HomeScreen: Screen("main_screen")
    object BannerScreen: Screen("banner_screen")
    object InterstitialScreen: Screen("interstitial_screen")
    object UniversalScreen: Screen("universal_screen")
}
