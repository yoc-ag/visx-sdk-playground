package com.yoc.visx.jetpackcompose.screen.banner

import android.content.Context
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.yoc.visx.jetpackcompose.common.Constants
import com.yoc.visx.sdk.VisxAdManager
import com.yoc.visx.sdk.mraid.EnhancedMraidProperties
import com.yoc.visx.sdk.util.AdSize
import com.yoc.visx.sdk.view.category.ActionTracker
import com.yoc.visx.sdk.view.category.AdActionTracker
import timber.log.Timber

/**
 * Class responsible for VIS.X Banner Ad Handling
 *
 * @param context       - app/activity context
 * @param adContainer   - where VIS.X Banner Ad is added
 */
class VisxBanner(private val context: Context, private val adContainer: RelativeLayout) {

    private lateinit var visxAdManager: VisxAdManager

    companion object {
        private const val BANNER_TAG = "---> VISX.Banner"
    }

    /**
     * Standard initialization of VIS.X Banner Ad
     * Link: https://support.yoc.com/android-sdk/integration/yoc-universal-ad-unit/
     *
     * This method invocation is mandatory for desired Universal AD to
     * have Understitial effect:
     *  - visxAdManagerUniversal.setMaxSizeHeight(maxSizeHeight) -
     */
    fun init(): VisxAdManager {
        visxAdManager = VisxAdManager.Builder()
            .context(context)
            .callback(object : ActionTracker {

                /**
                 * Overridden callback functions for tracking AD Creative Handling
                 * by the VIS.X SDK, like start, loading, received, finished and error event.
                 */
                override fun onAdRequestStarted(visxAdManager: VisxAdManager?) {
                    Timber.i("$BANNER_TAG onAdRequestStarted()")
                }

                override fun onAdResponseReceived(visxAdManager: VisxAdManager?, message: String?) {
                    Timber.i("$BANNER_TAG onAdResponseReceived() $message")
                }

                override fun onAdLoadingStarted(visxAdManager: VisxAdManager?) {
                    Timber.i("$BANNER_TAG onAdLoadingStarted()")
                }

                override fun onAdLoadingFinished(visxAdManager: VisxAdManager?, message: String?) {
                    Timber.i("$BANNER_TAG onAdLoadingFinished() $message")
                    displayAd(visxAdManager)
                }

                override fun onAdLoadingFailed(
                    visxAdManager: VisxAdManager?,
                    message: String?,
                    isFinal: Boolean
                ) {
                    Timber.i("$BANNER_TAG onAdLoadingFailed() $message")
                }

                override fun onAdSizeChanged(width: Int, height: Int) {
                    Timber.i("$BANNER_TAG onAdSizeChanged() -> width: $width height: $height")
                    adjustAdContainerSize(width, height)
                }

                override fun onAdClicked() {
                    Timber.i("$BANNER_TAG onAdClicked()")
                }

                override fun onAdLeftApplication() {
                    Timber.i("$BANNER_TAG onAdLeftApplication()")
                }

                override fun onVideoFinished() {
                    Timber.i("$BANNER_TAG onAdLeftApplication()")
                }

            })
            .visxAdUnitID(Constants.YOC_CLASSIC_BANNER_VIS_X_AD_UNIT_ID)
            .adSize(AdSize.MEDIUM_RECTANGLE_300x250)
            .appDomain(Constants.APP_DOMAIN)
            /**
             * param .isFixedSize(boolean) is needed for fix sized ads
             */
            .isFixedSize(true)
            .build()


        visxAdManager.setAdActionTracker(object : AdActionTracker {
            override fun onInterstitialWillBeClosed() {
                Timber.i("$BANNER_TAG AdActionTracker | onInterstitialWillBeClosed()")
            }

            override fun onInterstitialClosed() {
                Timber.i("$BANNER_TAG AdActionTracker | onInterstitialClosed()")
            }

            override fun onLandingPageOpened(inExternalBrowser: Boolean) {
                if (inExternalBrowser) {
                    Timber.i("$BANNER_TAG AdActionTracker | onLandingPageOpened. inExternalBrowser = $inExternalBrowser")
                } else {
                    Timber.i("$BANNER_TAG AdActionTracker | onLandingPageOpened. inExternalBrowser =  $inExternalBrowser")
                }
            }

            override fun onLandingPageClosed() {
                Timber.i("$BANNER_TAG AdActionTracker | onLandingPageClosed()")
            }

            override fun onAdResized(
                width: Int,
                height: Int,
                posX: Int,
                posY: Int,
                closeBtnPos: EnhancedMraidProperties.CloseButtonPosition
            ) {
                Timber.i("$BANNER_TAG AdActionTracker | onAdSpaceResized. width: $width, height: $height, posX: $posX, posY: $posY, closeBtnPos: $closeBtnPos")
            }

            override fun onResizedAdClosed() {
                Timber.i("$BANNER_TAG AdActionTracker | onResizedAdClosed()")
            }

        })

        return visxAdManager
    }

    /**
     * Adds VIS.X Banner Ad View in provided adContainer
     */
    private fun displayAd(visxAdManager: VisxAdManager?) {
        adContainer.addView(visxAdManager?.adContainer)
    }

    /**
     * Adjust height of provided adContainer
     */
    private fun adjustAdContainerSize(width: Int, height: Int) {
        val widthDp: Int = (width * context.resources.displayMetrics.density).toInt()
        val heightDp: Int = (height * context.resources.displayMetrics.density).toInt()
        adContainer.layoutParams = LinearLayout.LayoutParams(widthDp, heightDp)
    }
}