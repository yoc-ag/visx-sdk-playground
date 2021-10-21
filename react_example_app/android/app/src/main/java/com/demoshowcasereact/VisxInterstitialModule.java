package com.demoshowcasereact;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.yoc.visx.sdk.VisxAdManager;
import com.yoc.visx.sdk.util.AdSize;
import com.yoc.visx.sdk.view.category.ActionTracker;

/**
 * Handling and displaying VIS.X Mystery (Interstitial) Ad
 */
public class VisxInterstitialModule extends ReactContextBaseJavaModule {

    /**
     * Context needed for VIS.X Ad init
     */
    ReactApplicationContext context;

    /**
     * VIS.X Ad Instance
     */
    VisxAdManager visxAdMng;

    VisxInterstitialModule(ReactApplicationContext context) {
        super(context);
        this.context = context;

    }

    /**
     * Setup module name
     *
     * @return String
     */
    @NonNull
    @Override
    public String getName() {
        return "VisxInterstitialModule";
    }

    /**
     * Initialize VIS.X Ad with required params like Ad Unit ID, Domain and Size
     *
     * @param adId       - String
     * @param domainName - String
     */
    @ReactMethod
    public void getVisxAdManager(String adId, String domainName) {
        Log.d("VisxInterstitialModule", "Create visxAdManager called with ad ID: " + adId
                + " and domain name: " + domainName);

        /**
         * Must be run on React-Native UI thread for platform sync
         */
        getReactApplicationContext().runOnUiQueueThread(() -> {

            /**
             * VIS.X Ad Builder init
             */
            VisxAdManager visxAdManager = new VisxAdManager.Builder()
                    .visxAdUnitID(adId)
                    .adSize(AdSize.INTERSTITIAL_320x480)
                    .appDomain(domainName)
                    .context(getReactApplicationContext().getCurrentActivity())
                    .callback(new ActionTracker() {

                        /**
                         * VIS.X Ad callbacks
                         */
                        @Override
                        public void onAdRequestStarted(VisxAdManager visxAdManager) {
                            Log.d("VisxInterstitialModule", "onAdRequestStarted");
                        }

                        @Override
                        public void onAdResponseReceived(VisxAdManager visxAdManager, String s) {
                            Log.d("VisxInterstitialModule", "onAdResponseReceived: " + s);
                            visxAdMng = visxAdManager;

                            /**
                             * Display VIS.X Mystery (Interstitial) Ad
                             */
                            visxAdManager.showModalInterstitial();
                        }

                        @Override
                        public void onAdLoadingStarted(VisxAdManager visxAdManager) {
                            Log.d("VisxInterstitialModule", "onAdLoadingStarted");
                        }

                        @Override
                        public void onAdLoadingFinished(VisxAdManager visxAdManager, String s) {
                            Log.d("VisxInterstitialModule", "onAdLoadingFinished");
                        }

                        @Override
                        public void onAdLoadingFailed(VisxAdManager visxAdManager, String s, boolean b) {
                            Log.d("VisxInterstitialModule", "onAdLoadingFailed reason: " + s);
                        }

                        @Override
                        public void onAdSizeChanged(int width, int height) {

                        }

                        @Override
                        public void onAdClicked() {
                            Log.d("VisxInterstitialModule", "onAdClicked");
                        }

                        @Override
                        public void onAdLeftApplication() {
                            Log.d("VisxInterstitialModule", "onAdLeftApplication");
                        }
                    }).build();
        });
    }
}
