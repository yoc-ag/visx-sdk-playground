package com.yoc.demo_showcase_flutter

import android.content.Context
import android.content.MutableContextWrapper
import com.yoc.demo_showcase_flutter.Constants.AD_TYPE_KEY
import com.yoc.demo_showcase_flutter.Constants.AD_UNIT_ID
import com.yoc.demo_showcase_flutter.Constants.BANNER
import com.yoc.demo_showcase_flutter.Constants.UNIVERSAL
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

/**
 * Responsible for invocation desired View from Android Native to Flutter platform
 *
 * @implements PlatformViewFactory
 * @param      mainActivity
 */
class NativeViewFactory(private var mainActivity: MainActivity) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {

        /**
         * Parameters received from Flutter platform as Map
         */
        val creationParams = args as Map<String?, Any?>?

        /**
         * Maximum available screen height for ad to resize
         */
        val maxSizeHeight = getMaxSizeHeight(creationParams)

        /**
         * Ad TYPE for calling proper VisxAd View
         */
        val adType = getAdType(creationParams)

        /**
         * Ad UNIT ID for calling proper ad
         */
        val adUnitId = getAdUnitId(creationParams)

        /**
         * Return VisxAd View based on adType
         */
        return if ((adType == UNIVERSAL)) {
            VisxUniversalView((context as MutableContextWrapper).baseContext, mainActivity, maxSizeHeight, adUnitId)
        } else {
            VisxBannerView((context as MutableContextWrapper).baseContext, adUnitId)
        }
    }

    /**
     * Check if value for maximum available screen height is correct
     */
    private fun getMaxSizeHeight(creationParams: Map<String?, Any?>?): Int {
        return if (creationParams?.get(Constants.MAX_SIZE_HEIGHT) is Int) {
            creationParams[Constants.MAX_SIZE_HEIGHT] as Int
        } else {
            0
        }
    }

    /**
     * Returns the ad TYPE specified in Flutter platform
     *
     * BANNER or UNIVERSAL
     */
    private fun getAdType(creationParams: Map<String?, Any?>?): String {
        return if (creationParams?.get(AD_TYPE_KEY) is String) {
            creationParams[AD_TYPE_KEY] as String
        } else {
            BANNER
        }
    }

    /**
     * Returns the adUnit ID specified in Flutter platform
     *
     * String
     */
    private fun getAdUnitId(creationParams: Map<String?, Any?>?): String {
        return if (creationParams?.get(AD_UNIT_ID) is String) {
            creationParams[AD_UNIT_ID] as String
        } else {
            ""
        }
    }
}