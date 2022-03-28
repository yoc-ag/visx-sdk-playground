package com.yoc.demo_showcase_flutter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.yoc.demo_showcase_flutter.Constants.DOMAIN
import com.yoc.visx.sdk.VisxAdManager
import com.yoc.visx.sdk.adview.tracker.VisxCallbacks
import com.yoc.visx.sdk.mraid.properties.EnhancedMraidProperties
import com.yoc.visx.sdk.util.ad.AdSize
import io.flutter.plugin.platform.PlatformView

/**
 * Setting up VIS.X Universal AD
 *
 * Extends PlatformView for returning VisxAd View to Flutter platform
 * Extends RelativeLayout (Native VisxAd View Container layout)
 *
 * @param context       - for creating VisxAd
 * @param mainActivity  - for communicating with Flutter platform
 * @param maxSizeHeight - height of the maximum available screen height for displaying ad,
 *                        received from Flutter platform
 */
class FlutterVisxUniversalView(
    context: Context, private val mainActivity: MainActivity, private val maxSizeHeight: Int,
    private val adUnitId: String
) : PlatformView, RelativeLayout(context) {

    companion object {
        const val TAG = "VisxModule.Universal"
    }

    private lateinit var visxAdManagerUniversal: VisxAdManager

    /**
     * Inflating the view layout
     * @see - res\layout\universal_view.xml
     */
    init {
        inflate(context, R.layout.universal_view, this)
        initVisxAdView()
    }

    /**
     * Returns this (VisxUniversalView) to Flutter platform in adContainer
     */
    override fun getView(): View {
        return this
    }

    /**
     * Stopping visxAdManager
     */
    override fun dispose() {
        if (::visxAdManagerUniversal.isInitialized) {
            visxAdManagerUniversal.stop()
        }
    }

    /**
     * Standard initialization of VIS.X Universal Ad
     * Link: https://support.yoc.com/android-sdk/integration/yoc-universal-ad-unit/
     *
     * This method invocation is mandatory for desired Universal AD to
     * have Understitial effect
     *  - visxAdManagerUniversal.setMaxSizeHeight(maxSizeHeight) -
     */
    private fun initVisxAdView() {
        visxAdManagerUniversal = VisxAdManager.Builder()
            .visxAdUnitID(adUnitId)
            .adSize(AdSize.MEDIUM_RECTANGLE_300x250)
            .appDomain(DOMAIN)
            .context(context)
            .callback(object : VisxCallbacks() {
                override fun onAdRequestStarted(visxAdManager: VisxAdManager?) {
                    Log.d(TAG, "onAdRequestStarted")
                }

                override fun onAdResponseReceived(visxAdManager: VisxAdManager?, s: String?) {
                    Log.d(TAG, "onAdResponseReceived: $s")
                    displayAd()
                }

                override fun onAdLoadingStarted(visxAdManager: VisxAdManager?) {
                    Log.d(TAG, "onAdLoadingStarted")
                }

                override fun onAdLoadingFinished(visxAdManager: VisxAdManager?, s: String?) {
                    Log.d(TAG, "onAdLoadingFinished: $s")
                    displayAd()
                    registerListener()
                }

                override fun onAdLoadingFailed(message: String?, errorCode: Int, isFinal: Boolean) {

                    Log.d(TAG, "onAdLoadingFailed message: $message errorCode: $errorCode")

                    /**
                     * Collapse Flutter adContainer if ad fails to load
                     */
                    mainActivity.updateAdContainer(0, 0)
                }

                override fun onAdSizeChanged(width: Int, height: Int) {
                    Log.d(
                        TAG, "onAdSizeChanged width: " + width
                                + " height: " + height
                    )

                    /**
                     * Expand Flutter adContainer with adSize values
                     */
                    mainActivity.updateAdContainer(width, height)
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
                    Log.d(TAG, " onInterstitialClosed")
                }

                override fun onLandingPageOpened(b: Boolean) {
                    Log.d(TAG, " onLandingPageOpened: $b")
                }

                override fun onLandingPageClosed() {
                    Log.d(TAG, " onLandingPageClosed")
                }

                override fun onAdResized(
                        p0: Int,
                        p1: Int,
                        p2: Int,
                        p3: Int,
                        p4: EnhancedMraidProperties.CloseButtonPosition?
                ) {
                    Log.d(TAG, " onAdResized")
                }

                override fun onResizedAdClosed() {
                    Log.d(TAG, " onResizedAdClosed")
                }

                override fun onAdClosed() {
                    Log.d(TAG, " onAdClosed")
                }

                override fun onAdResumeApplication() {
                    Log.d(TAG, " onAdResumeApplication")
                }

                override fun onAdViewable() {
                    Log.d(TAG, " onAdViewable")
                }

                override fun onEffectChange(effect: String?) {
                    Log.d(TAG, " onEffectChange effect: $effect")
                }

                override fun onVideoFinished() {
                    Log.d(TAG, " onVideoFinished")
                }

            })
            .build()

        /**
         * Set maximum available size in height for ad to resize automatically
         */
        visxAdManagerUniversal.setMaxSizeHeight(maxSizeHeight)
    }

    /**
     * Called when ad is ready to be displayed
     */
    private fun displayAd() {
        Log.i(TAG, "SDK Version: " + visxAdManagerUniversal.sdKVersion)
        (context as Activity).runOnUiThread {

            /**
             * @see - res\layout\universal_view.xml
             */
            val inlineContainer = findViewById<RelativeLayout>(R.id.inlineContainer)

            /**
             * Check if the view has parent
             */
            if (visxAdManagerUniversal.adContainer.parent != null) {
                (visxAdManagerUniversal.adContainer.parent as ViewGroup).removeView(
                    visxAdManagerUniversal.adContainer
                )
            }
            inlineContainer.addView(visxAdManagerUniversal.adContainer)
            inlineContainer.invalidate()
            inlineContainer.postInvalidate()
        }
    }

    /**
     * Y position of adContainer of Flutter adContainer
     */
    private var y = 0.0

    /**
     * Listener for variables changes regarding Flutter adContainer
     */
    private fun registerListener() {
        mainActivity.viewDataObserversY.add { y ->
            this.y = y.toDouble()

            /**
             * Tracking y position of the Flutter adContainer
             *
             * All other values (exposureChange, x, etc. are tracked also)
             */
            visxAdManagerUniversal.setViewValues(this.y)
        }
    }
}