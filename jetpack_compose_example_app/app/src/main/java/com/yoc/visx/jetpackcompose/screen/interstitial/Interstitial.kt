package com.yoc.visx.jetpackcompose.screen.interstitial

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yoc.visx.jetpackcompose.R
import com.yoc.visx.jetpackcompose.common.Constants.APP_DOMAIN
import com.yoc.visx.jetpackcompose.common.Constants.YOC_INTERSTITIAL_VIS_X_AD_UNIT_ID
import com.yoc.visx.jetpackcompose.ui.components.CustomButton
import com.yoc.visx.jetpackcompose.ui.components.DummyText
import com.yoc.visx.jetpackcompose.ui.components.HeaderImage
import com.yoc.visx.jetpackcompose.ui.components.HeaderText
import com.yoc.visx.jetpackcompose.ui.theme.Demo_showcase_jetpack_composeTheme
import com.yoc.visx.jetpackcompose.ui.theme.colorPrimaryDark
import com.yoc.visx.jetpackcompose.ui.theme.whiteSmoke
import com.yoc.visx.sdk.adview.VisxAd

@Composable
fun InterstitialScreen(context: Context, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = context.getString(R.string.visx_interstitial_showcase))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },
                backgroundColor = colorPrimaryDark,
                contentColor = whiteSmoke
            )
        }, content = {
            Demo_showcase_jetpack_composeTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(whiteSmoke)
                        .scrollable(
                            orientation = Orientation.Vertical,
                            state = rememberScrollableState { delta ->
                                delta
                            })
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        HeaderImage()
                        HeaderText()
                        CustomButton(
                            text = context.getString(R.string.display_interstitial)
                        ) { displayInterstitial(context) }
                        Spacer(modifier = Modifier.height(16.dp))
                        DummyText(context = context)
                        DummyText(context = context)
                    }
                }
            }
        })
}

/**
 * Initialization and displaying VIS.X Mystery (Interstitial) Ad
 * @Link https://support.yoc.com/android-sdk/integration/yoc-mystery-ad/#interstitial-ad-hoc
 */
fun displayInterstitial(context: Context) {
    VisxAd(context).displayInterstitial(YOC_INTERSTITIAL_VIS_X_AD_UNIT_ID, APP_DOMAIN)
}