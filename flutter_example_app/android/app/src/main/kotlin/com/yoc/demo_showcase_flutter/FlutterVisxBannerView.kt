package com.yoc.demo_showcase_flutter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.yoc.visx.sdk.VisxAdManager
import com.yoc.visx.sdk.mraid.EnhancedMraidProperties
import com.yoc.visx.sdk.util.AdSize
import com.yoc.visx.sdk.view.category.ActionTracker
import com.yoc.visx.sdk.view.category.AdActionTracker
import io.flutter.plugin.platform.PlatformView

internal class FlutterVisxBannerView(private val context: Context, private var mainActivity: MainActivity
) : PlatformView {

    companion object {
        const val TAG = "VisxModule.Banner"
        const val BANNER_AD_ID = "912261"
        const val DOMAIN = "yoc.com"
        const val ACTION_TRACKER = "ActionTracker"
        const val AD_ACTION_TRACKER = "AdActionTracker"
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
                .visxAdUnitID(BANNER_AD_ID)
                .adSize(AdSize.MEDIUM_RECTANGLE_300x250)
                .appDomain(DOMAIN)
                .context(context)
                .isFixedSize(true)
                .callback(object : ActionTracker {
                    override fun onAdRequestStarted(visxAdManager: VisxAdManager?) {
                        Log.d(TAG, "$ACTION_TRACKER onAdRequestStarted")
                    }

                    override fun onAdResponseReceived(visxAdManager: VisxAdManager?, s: String?) {
                        Log.d(TAG, "$ACTION_TRACKER onAdResponseReceived: $s")
                    }

                    override fun onAdLoadingStarted(visxAdManager: VisxAdManager?) {
                        Log.d(TAG, "$ACTION_TRACKER onAdLoadingStarted")
                    }

                    override fun onAdLoadingFinished(visxAdManager: VisxAdManager?, s: String?) {
                        Log.d(TAG, "$ACTION_TRACKER onAdLoadingFinished: $s")
                        displayAd()
                    }

                    override fun onAdLoadingFailed(visxAdManager: VisxAdManager?, s: String?, p2: Boolean) {
                        Log.d(TAG, "$ACTION_TRACKER onAdLoadingFailed reason: $s")
                    }

                    override fun onAdSizeChanged(width: Int, height: Int) {
                        Log.d(TAG, ACTION_TRACKER + " onAdSizeChanged width: " + width
                                + " height: " + height)
                    }

                    override fun onAdClicked() {
                        Log.d(TAG, "$ACTION_TRACKER onAdClicked")
                    }

                    override fun onAdLeftApplication() {
                        Log.d(TAG, "$ACTION_TRACKER onAdLeftApplication")
                    }

                })
                .build()

        visxAdManagerBanner.setAdActionTracker(object : AdActionTracker {
            override fun onInterstitialWillBeClosed() {
                Log.d(TAG, "$AD_ACTION_TRACKER onInterstitialWillBeClosed")
            }

            override fun onInterstitialClosed() {
                Log.d(TAG, "$AD_ACTION_TRACKER onInterstitialClosed")
            }

            override fun onLandingPageOpened(b: Boolean) {
                Log.d(TAG, "$AD_ACTION_TRACKER onLandingPageOpened: $b")
            }

            override fun onLandingPageClosed() {
                Log.d(TAG, "$AD_ACTION_TRACKER onLandingPageClosed")
            }

            override fun onAdResized(p0: Int, p1: Int, p2: Int, p3: Int, p4: EnhancedMraidProperties.CloseButtonPosition?) {
                Log.d(TAG, "$AD_ACTION_TRACKER onAdResized")
            }

            override fun onResizedAdClosed() {
                Log.d(TAG, "$AD_ACTION_TRACKER onResizedAdClosed")
            }
        })
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