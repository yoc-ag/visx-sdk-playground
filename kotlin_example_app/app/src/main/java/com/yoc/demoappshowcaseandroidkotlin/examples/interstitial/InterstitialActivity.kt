package com.yoc.demoappshowcaseandroidkotlin.examples.interstitial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.yoc.demoappshowcaseandroidkotlin.databinding.ActivityInterstitialBinding
import com.yoc.visx.sdk.VisxAdManager
import com.yoc.visx.sdk.adview.VisxAd
import com.yoc.visx.sdk.adview.tracker.VisxCallbacks
import com.yoc.visx.sdk.util.ad.AdSize
import timber.log.Timber

class InterstitialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterstitialBinding
    private lateinit var visxAdManager: VisxAdManager

    companion object {
        private const val INTERSTITIAL_TAG = "---> VISX.Interstitial"
        fun start(context: Context) {
            context.startActivity(Intent(context, InterstitialActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterstitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Interstitial"
        initButtons()
    }

    private fun initButtons() {
        binding.loadAdButton.setOnClickListener {
            binding.progressBar.root.visibility = View.VISIBLE
            initVisxInterstitial()
        }
        binding.showAdButton.setOnClickListener {
            if (::visxAdManager.isInitialized) {
                visxAdManager.showModalInterstitial()
            } else {
                Snackbar.make(binding.root, "Interstitial not loaded", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.autoInterstitialButton.setOnClickListener {
            VisxAd(this).displayInterstitial("912263", "yoc.com")
        }
    }

    private fun initVisxInterstitial() {
        visxAdManager = VisxAdManager.Builder()
            .context(this)
            .callback(getVisxCallbacks())
            .visxAdUnitID("912263")
            .adSize(AdSize.INTERSTITIAL_320x480)
            .appDomain("yoc.com")
            .build()
    }

    private fun getVisxCallbacks(): VisxCallbacks {
        return object : VisxCallbacks() {
            override fun onAdRequestStarted(visxAdManager: VisxAdManager?) {
                Timber.i("$INTERSTITIAL_TAG onAdRequestStarted()")
            }

            override fun onAdResponseReceived(visxAdManager: VisxAdManager?, message: String?) {
                Timber.i("$INTERSTITIAL_TAG onAdResponseReceived() $message")
            }

            override fun onAdLoadingFinished(visxAdManager: VisxAdManager?, message: String?) {
                Timber.i("$INTERSTITIAL_TAG onAdLoadingFinished() $message")
                binding.progressBar.root.visibility = View.GONE
                Snackbar.make(binding.root, "Interstitial loaded", Snackbar.LENGTH_SHORT).show()
            }

            override fun onAdLoadingFailed(message: String?, errorCode: Int, isFinal: Boolean) {
                Timber.i("$INTERSTITIAL_TAG onAdLoadingFailed() $message errorCode: $errorCode")
                binding.progressBar.root.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    "Interstitial failed, check logs for error message and code",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onAdSizeChanged(width: Int, height: Int) {
                Timber.i("$INTERSTITIAL_TAG onAdSizeChanged()")
            }

            override fun onAdClicked() {
                Timber.i("$INTERSTITIAL_TAG onAdClicked()")
            }

            override fun onAdLeftApplication() {
                Timber.i("$INTERSTITIAL_TAG onAdLeftApplication()")
            }

            override fun onAdResumeApplication() {
                Timber.i("$INTERSTITIAL_TAG onAdResumeApplication()")
            }

            override fun onVideoFinished() {
                Timber.i("$INTERSTITIAL_TAG onVideoFinished()")
            }

            override fun onEffectChange(effect: String?) {
                Timber.i("$INTERSTITIAL_TAG onEffectChange() -> effect: $effect")
            }

            override fun onAdViewable() {
                Timber.i("$INTERSTITIAL_TAG onAdViewable()")
            }

            override fun onLandingPageOpened(inExternalBrowser: Boolean) {
                Timber.i("$INTERSTITIAL_TAG onLandingPageOpened. inExternalBrowser = $inExternalBrowser")
            }

            override fun onInterstitialWillBeClosed() {
                Timber.i("$INTERSTITIAL_TAG onInterstitialWillBeClosed()")
            }

            override fun onInterstitialClosed() {
                Timber.i("$INTERSTITIAL_TAG onInterstitialClosed()")
            }

            override fun onAdClosed() {
                Timber.i("$INTERSTITIAL_TAG onAdClosed()")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::visxAdManager.isInitialized) {
            visxAdManager.stop()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}