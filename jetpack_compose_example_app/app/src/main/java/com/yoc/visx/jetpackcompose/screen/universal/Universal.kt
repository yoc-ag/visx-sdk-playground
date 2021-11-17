package com.yoc.visx.jetpackcompose.screen.universal

import android.content.Context
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.RelativeLayout
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
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
 * and .setViewValues(y) for applying Understitial effect
 */
private lateinit var visxAdManager: VisxAdManager

/**
 * Maximum available height of the screen part of the Client App
 * where VIS.X Universal Ad can be displayed in dp
 */
private var maxScreenHeight = 0

/**
 * Height of the AppBar in dp
 */
private var appBarOffset = 0.0f

/**
 * Check if the Composable is rendered on screen so it can measure screen size of the Scaffold
 * and TopBar for setting maxScreenHeight param
 */
var isDisplayed = false

/**
 * Composable function with view hierarchy where VIS.X Universal Ad is set
 */
@Composable
fun UniversalScreen(context: Context, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = context.getString(R.string.visx_universal_showcase))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },
                backgroundColor = colorPrimaryDark,
                contentColor = whiteSmoke,
                modifier = Modifier.onGloballyPositioned {
                    /**
                     * Measure TopBar (AppBar) height in dp
                     */
                    appBarOffset = it.size.height / getDensity(context)
                }
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
                        .onGloballyPositioned { layoutCoordinates ->
                            /**
                             * Setting maxScreenHeight in dp
                             */
                            maxScreenHeight = (layoutCoordinates.size.height / getDensity(context)).toInt()

                            /**
                             * Setting the flag for displaying VIS.X Universal Ad with needed parameters for maxScreenHeight
                             */
                            isDisplayed = true
                        }
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

                        if (isDisplayed) {
                            /**
                             * Native Android View serving as adContainer
                             * where VIS.X Universal Ad is initialized and displayed
                             */
                            AndroidView(
                                factory = { ctx ->
                                    RelativeLayout(ctx).apply {
                                        /**
                                         * Preparing adContainer Layout parameters to have max available width and
                                         * to be wrapped in height for VIS.X Universal Ad can calculate max width
                                         * available to expand in height properly.
                                         */
                                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                                        val visxUniversal = VisxUniversal(context, this)
                                        visxAdManager = visxUniversal.init(maxScreenHeight)
                                    }
                                }, modifier = Modifier.onGloballyPositioned {
                                    /**
                                     * Top left position of the adContainer on screen in parent for calculating
                                     * and applying Understitial effect if necessary
                                     */
                                    val y = it.positionInRoot().y.toDouble() / getDensity(context) - appBarOffset
                                    visxAdManager.setViewValues(y)
                                })
                        }
                        DummyText(context)
                    }
                }
            }
        })

    /**
     * Disposing of VisxAdManager instance and stopping VIS.X Universal Ad
     */
    DisposableEffect(Unit) {
        onDispose {
            if (::visxAdManager.isInitialized) {
                visxAdManager.stop()
            }
        }
    }
}

fun getDensity(context: Context): Float {
    return context.resources.displayMetrics.density
}