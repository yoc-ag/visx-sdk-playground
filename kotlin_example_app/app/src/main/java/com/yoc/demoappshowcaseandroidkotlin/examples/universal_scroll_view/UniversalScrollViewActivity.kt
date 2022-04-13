package com.yoc.demoappshowcaseandroidkotlin.examples.universal_scroll_view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yoc.demoappshowcaseandroidkotlin.databinding.ActivityUniversalScrollViewBinding
import com.yoc.visx.sdk.VisxAdManager
import com.yoc.visx.sdk.adview.tracker.VisxCallbacks
import com.yoc.visx.sdk.util.ad.AdSize
import timber.log.Timber

class UniversalScrollViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUniversalScrollViewBinding
    private lateinit var visxAdManager: VisxAdManager

    companion object {
        private const val UNIVERSAL_TAG = "---> VISX.Universal"
        fun start(context: Context) {
            context.startActivity(Intent(context, UniversalScrollViewActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUniversalScrollViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Universal ScrollView"
        initVisxUniversalAd()
    }

    private fun initVisxUniversalAd() {
        visxAdManager = VisxAdManager.Builder()
            .context(this)
            .callback(getVisxCallbacks())
            .visxAdUnitID("912268")
            .adSize(AdSize.SMARTPHONE_320x50)
            .appDomain("yoc.com")
            .anchorView(binding.scrollView)
            .build()
    }

    private fun getVisxCallbacks(): VisxCallbacks {
        return object : VisxCallbacks() {
            override fun onAdRequestStarted(visxAdManager: VisxAdManager?) {
                Timber.i("$UNIVERSAL_TAG onAdRequestStarted()")
            }

            override fun onAdResponseReceived(visxAdManager: VisxAdManager?, message: String?) {
                Timber.i("$UNIVERSAL_TAG onAdResponseReceived()")
            }

            override fun onAdLoadingFinished(visxAdManager: VisxAdManager?, message: String?) {
                Timber.i("$UNIVERSAL_TAG onAdLoadingFinished()")
                displayAd(visxAdManager)
            }

            override fun onAdLoadingFailed(message: String?, errorCode: Int, isFinal: Boolean) {
                Timber.i("$UNIVERSAL_TAG onAdLoadingFailed() message: $message errorCode: $errorCode")
            }

            override fun onAdSizeChanged(width: Int, height: Int) {
                Timber.i("$UNIVERSAL_TAG onAdSizeChanged() width: $width height: $height")
            }

            override fun onAdClicked() {
                Timber.i("$UNIVERSAL_TAG onAdClicked()")
            }

            override fun onAdLeftApplication() {
                Timber.i("$UNIVERSAL_TAG onAdLeftApplication()")
            }

            override fun onAdResumeApplication() {
                Timber.i("$UNIVERSAL_TAG onAdResumeApplication()")
            }

            override fun onVideoFinished() {
                Timber.i("$UNIVERSAL_TAG onVideoFinished()")
            }

            override fun onEffectChange(effect: String?) {
                Timber.i("$UNIVERSAL_TAG onEffectChange() -> effect: $effect")
            }

            override fun onAdViewable() {
                Timber.i("$UNIVERSAL_TAG onAdViewable()")
            }

            override fun onLandingPageOpened(inExternalBrowser: Boolean) {
                if (inExternalBrowser) {
                    Timber.i("$UNIVERSAL_TAG onLandingPageOpened. inExternalBrowser = $inExternalBrowser")
                } else {
                    Timber.i("$UNIVERSAL_TAG onLandingPageOpened. inExternalBrowser =  $inExternalBrowser")
                }
            }

            override fun onAdClosed() {
                Timber.i("$UNIVERSAL_TAG onAdClosed()")
            }
        }
    }

    private fun displayAd(visxAdMgr: VisxAdManager?) {
        binding.inlineContainer.visibility = View.VISIBLE
        binding.inlineContainer.removeAllViews()
        binding.inlineContainer.addView(visxAdMgr?.adContainer)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::visxAdManager.isInitialized) {
            visxAdManager.stop()
        }
    }
}