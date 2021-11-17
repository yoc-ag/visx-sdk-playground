package com.yoc.visx.jetpackcompose.screen.universal

import android.content.Context
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
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
 * Class responsible for VIS.X Universal Ad Handling
 *
 * @param context       - app/activity context
 * @param adContainer   - where VIS.X Universal Ad is added
 */
class VisxUniversal (private val context: Context, private val adContainer: RelativeLayout) {

    private lateinit var visxAdManager: VisxAdManager

    companion object {
        private const val UNIVERSAL_TAG = "---> VISX.Universal"
    }

    /**
     * Standard initialization of VIS.X Universal Ad
     * Link: https://support.yoc.com/android-sdk/integration/yoc-universal-ad-unit/
     *
     * This method invocation is mandatory for desired Universal AD to
     * have Understitial effect:
     *  - visxAdManagerUniversal.setMaxSizeHeight(maxSizeHeight) -
     */
    fun init(maxScreenHeight: Int): VisxAdManager {
        visxAdManager = VisxAdManager.Builder()
            .context(context)
            .callback(object : ActionTracker {
                override fun onAdRequestStarted(visxAdManager: VisxAdManager?) {
                    Timber.i("$UNIVERSAL_TAG onAdRequestStarted()")
                }

                override fun onAdResponseReceived(visxAdManager: VisxAdManager?, message: String?) {
                    Timber.i("$UNIVERSAL_TAG onAdResponseReceived() $message")
                }

                override fun onAdLoadingStarted(visxAdManager: VisxAdManager?) {
                    Timber.i("$UNIVERSAL_TAG onAdLoadingStarted()")
                }

                override fun onAdLoadingFinished(visxAdManager: VisxAdManager?, message: String?) {
                    Timber.i("$UNIVERSAL_TAG onAdLoadingFinished() $message")
                    displayAd(visxAdManager)
                }

                override fun onAdLoadingFailed(
                    visxAdManager: VisxAdManager?,
                    message: String?,
                    isFinal: Boolean
                ) {
                    Timber.i("$UNIVERSAL_TAG onAdLoadingFailed() $message")
                }

                override fun onAdSizeChanged(width: Int, height: Int) {
                    Timber.i("$UNIVERSAL_TAG onAdSizeChanged() -> width: $width height: $height")
                    adjustAdContainerSize(width, height)
                }

                override fun onAdClicked() {
                    Timber.i("$UNIVERSAL_TAG onAdClicked()")
                }

                override fun onAdLeftApplication() {
                    Timber.i("$UNIVERSAL_TAG onAdLeftApplication()")
                }

                override fun onVideoFinished() {
                    Timber.i("$UNIVERSAL_TAG onAdLeftApplication()")

                    /**
                     * Collapse adContainer if ad fails to load
                     */
                    adjustAdContainerSize(0, 0)
                }

            })
            .visxAdUnitID(Constants.YOC_UNIVERSAL_AD_AD_UNIT_ID)
            .adSize(AdSize.MEDIUM_RECTANGLE_300x250)
            .appDomain(Constants.APP_DOMAIN)
            .build()

        /**
         * Set the maximum available screen height where the ad can be displayed
         */
        visxAdManager.setMaxSizeHeight(maxScreenHeight)

        /**
         * Set action tracker of VisxAdManager object for tracking
         * events like opening or closing and resizing of the AD Creative
         */
        visxAdManager.setAdActionTracker(object : AdActionTracker {
            override fun onInterstitialWillBeClosed() {
                Timber.i("$UNIVERSAL_TAG AdActionTracker | onInterstitialWillBeClosed()")
            }

            override fun onInterstitialClosed() {
                Timber.i("$UNIVERSAL_TAG AdActionTracker | onInterstitialClosed()")
            }

            override fun onLandingPageOpened(inExternalBrowser: Boolean) {
                if (inExternalBrowser) {
                    Timber.i("$UNIVERSAL_TAG AdActionTracker | onLandingPageOpened. inExternalBrowser = $inExternalBrowser")
                } else {
                    Timber.i("$UNIVERSAL_TAG AdActionTracker | onLandingPageOpened. inExternalBrowser =  $inExternalBrowser")
                }
            }

            override fun onLandingPageClosed() {
                Timber.i("$UNIVERSAL_TAG AdActionTracker | onLandingPageClosed()")
            }

            override fun onAdResized(
                width: Int,
                height: Int,
                posX: Int,
                posY: Int,
                closeBtnPos: EnhancedMraidProperties.CloseButtonPosition
            ) {
                Timber.i("$UNIVERSAL_TAG AdActionTracker | onAdSpaceResized. width: $width, height: $height, posX: $posX, posY: $posY, closeBtnPos: $closeBtnPos")
            }

            override fun onResizedAdClosed() {
                Timber.i("$UNIVERSAL_TAG AdActionTracker | onResizedAdClosed()")
            }
        })
        return visxAdManager
    }

    /**
     * Adds VIS.X Universal Ad View in provided adContainer
     */
    private fun displayAd(visxAdManager: VisxAdManager?) {
        adContainer.addView(visxAdManager?.adContainer)
    }

    /**
     * Adjust height of provided adContainer
     * - width is optional, if needed
     */
    private fun adjustAdContainerSize(width: Int, height: Int) {
        val widthDp: Int = (width * context.resources.displayMetrics.density).toInt()
        val heightDp: Int = (height * context.resources.displayMetrics.density).toInt()
        adContainer.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, heightDp)
    }
}