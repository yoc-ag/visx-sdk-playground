package com.yoc.demo_showcase_flutter

import android.content.Context
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.RelativeLayout
import com.yoc.visx.sdk.VisxAdManager
import com.yoc.visx.sdk.adview.tracker.VisxCallbacks
import io.flutter.plugin.platform.PlatformView

class VisxBannerView(private val context: Context, private val adUnitId: String) :
    PlatformView {

    companion object {
        const val TAG = "VisxModule.Banner"
    }


    lateinit var visxAdManagerBanner: VisxAdManager
    private val container: RelativeLayout = RelativeLayout(context)

    override fun getView(): View {
        return visxAdManagerBanner.adContainer
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
            .adSize(Size(300, 250))
            .context((context))
            .fixedSize()
            .callback(getCallbacks())
            .build()

    }

    private fun getCallbacks() : VisxCallbacks {
        return object : VisxCallbacks() {
            override fun onAdRequestStarted(visxAdManager: VisxAdManager) {
                super.onAdRequestStarted(visxAdManager)
            }

            override fun onAdResponseReceived(visxAdManager: VisxAdManager, price: Double, currency: String) {
                super.onAdResponseReceived(visxAdManager, price, currency)
            }

            override fun onAdLoadingFinished(visxAdManager: VisxAdManager, message: String) {
                super.onAdLoadingFinished(visxAdManager, message)
            }

            override fun onAdLoadingFailed(message: String, errorCode: Int, isFinal: Boolean) {
                super.onAdLoadingFailed(message, errorCode, isFinal)
            }
        }
    }

    private fun displayAd() {
        Log.i(TAG, "Displaying ad")
        container.addView(visxAdManagerBanner.adContainer)
    }

    override fun onFlutterViewDetached() {
        if (::visxAdManagerBanner.isInitialized) {
            visxAdManagerBanner.stop()
        }
    }
}
