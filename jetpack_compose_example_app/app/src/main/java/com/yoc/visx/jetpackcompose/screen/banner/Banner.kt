package com.yoc.visx.jetpackcompose.screen.banner

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.yoc.visx.jetpackcompose.R
import com.yoc.visx.jetpackcompose.ui.components.DummyText
import com.yoc.visx.jetpackcompose.ui.components.HeaderImage
import com.yoc.visx.jetpackcompose.ui.components.HeaderText
import com.yoc.visx.jetpackcompose.ui.theme.Demo_showcase_jetpack_composeTheme
import com.yoc.visx.jetpackcompose.ui.theme.colorPrimaryDark
import com.yoc.visx.jetpackcompose.ui.theme.whiteSmoke
import com.yoc.visx.sdk.VisxAdManager


/**
 * Instance of VisxAdManager. Needed for calling .stop() method on Disposable
 */
private lateinit var visxAdManager: VisxAdManager

/**
 * Composable function with view hierarchy where VIS.X Banner Ad is set
 */
@Composable
fun BannerScreen(context: Context, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = context.getString(R.string.visx_banner_showcase))
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
                        DummyText(context)
                        Spacer(modifier = Modifier.height(4.dp))

                        /**
                         * Native Android View serving as adContainer
                         * where VIS.X Banner Ad is initialized and displayed
                         */
                        AndroidView(factory = { ctx ->
                            android.widget.RelativeLayout(ctx).apply {
                                val visxBanner = VisxBanner(context, this)
                                visxAdManager = visxBanner.init()
                            }
                        })

                        Spacer(modifier = Modifier.height(4.dp))
                        DummyText(context)

                    }
                }
            }
        })

    /**
     * Disposing of VisxAdManager instance and stopping VIS.X Banner Ad
     */
    DisposableEffect(Unit) {
        onDispose {
            if (::visxAdManager.isInitialized) {
                visxAdManager.stop()
            }
        }
    }
}