package com.yoc.demo_showcase_flutter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.yoc.demo_showcase_flutter.Constants.DOMAIN
import com.yoc.visx.sdk.VisxAdManager
import com.yoc.visx.sdk.adview.tracker.VisxCallbacks
import com.yoc.visx.sdk.mraid.properties.EnhancedMraidProperties
import com.yoc.visx.sdk.util.ad.AdSize
import io.flutter.plugin.platform.PlatformView

internal class FlutterVisxBannerView(
    private val context: Context,
    private val adUnitId: String
) : PlatformView {

    companion object {
        const val TAG = "VisxModule.Banner"
    }


    lateinit var visxAdManagerBanner: VisxAdManager
    private val container: RelativeLayout = RelativeLayout(context)

    override fun getView(): View {
        return container
    }

    override fun dispose() {
        if (::visxAdManagerBanner.isInitialized) {
            visxAdManagerBanner.stop()
        }
    }

    init {
        initVisxAdView()
    }

    private fun initVisxAdView() {
        visxAdManagerBanner = VisxAdManager.Builder()
            .visxAdUnitID(adUnitId)
            .adSize(AdSize.MEDIUM_RECTANGLE_300x250)
            .appDomain(DOMAIN)
            .context(context)
            .isFixedSize(true)
            .callback(object : VisxCallbacks() {
                override fun onAdRequestStarted(visxAdManager: VisxAdManager?) {
                    Log.d(TAG, "onAdRequestStarted")
                }

                override fun onAdResponseReceived(visxAdManager: VisxAdManager?, s: String?) {
                    Log.d(TAG, "onAdResponseReceived: $s")
                }

                override fun onAdLoadingStarted(visxAdManager: VisxAdManager?) {
                    Log.d(TAG, "onAdLoadingStarted")
                }

                override fun onAdLoadingFinished(visxAdManager: VisxAdManager?, s: String?) {
                    Log.d(TAG, "onAdLoadingFinished: $s")
                    displayAd()
                }

                override fun onAdLoadingFailed(message: String?, errorCode: Int, isFinal: Boolean) {
                    Log.d(TAG, "onAdLoadingFailed message: $message errorCode: $errorCode")
                }

                override fun onAdSizeChanged(width: Int, height: Int) {
                    Log.d(
                        TAG, TAG + " onAdSizeChanged width: " + width
                                + " height: " + height
                    )
                }

                override fun onAdClicked() {
                    Log.d(TAG, "onAdClicked")
                }

                override fun onAdLeftApplication() {
                    Log.d(TAG, "onAdLeftApplication")
                }

                override fun onInterstitialWillBeClosed() {
                    Log.d(TAG, "onInterstitialWillBeClosed")
                }

                override fun onInterstitialClosed() {
                    Log.d(TAG, "onInterstitialClosed")
                }

                override fun onLandingPageOpened(b: Boolean) {
                    Log.d(TAG, "onLandingPageOpened: $b")
                }

                override fun onLandingPageClosed() {
                    Log.d(TAG, "onLandingPageClosed")
                }

                override fun onAdResized(
                        p0: Int,
                        p1: Int,
                        p2: Int,
                        p3: Int,
                        p4: EnhancedMraidProperties.CloseButtonPosition?
                ) {
                    Log.d(TAG, "onAdResized")
                }

                override fun onResizedAdClosed() {
                    Log.d(TAG, "onResizedAdClosed")
                }

            })
            .build()

    }

    private fun displayAd() {
        Log.i(TAG, "SDK Version: " + visxAdManagerBanner.sdKVersion)
        container.addView(visxAdManagerBanner.adContainer)
    }

    override fun onFlutterViewDetached() {
        if (::visxAdManagerBanner.isInitialized) {
            visxAdManagerBanner.stop()
        }
    }
}