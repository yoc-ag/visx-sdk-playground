package com.demoshowcasereact;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.yoc.visx.sdk.VisxAdManager;
import com.yoc.visx.sdk.mraid.EnhancedMraidProperties;
import com.yoc.visx.sdk.util.AdSize;
import com.yoc.visx.sdk.view.category.ActionTracker;
import com.yoc.visx.sdk.view.category.AdActionTracker;

/**
 * Handling VIS.X Universal Ad
 */
public class UniversalView extends RelativeLayout {

    private static final String TAG = "Visx-Universal";
    private static final String ACTION_TRACKER = "ActionTracker";
    private static final String AD_ACTION_TRACKER = "AdActionTracker";

    /**
     * Key values for size change mapping
     */
    private static final String KEY_WIDTH = "width";
    private static final String KEY_HEIGHT = "height";

    /**
     * VIS.X Ad Unit and Domain params
     */
    private static final String UNIVERSAL_AD_UNIT = "910967";
    private static final String DOMAIN = "yoc.com";

    /**
     * Context needed for VIS.X Ad init
     */
    private final Context context;

    /**
     * VIS.X Ad Instance
     */
    private VisxAdManager visxAdManager;

    /**
     * Event name for registering event channel for sending size data to React-Native App
     * for adContainer resize
     */
    private static final String EVENT_NAME = "sizeChange";

    /**
     * Constructor
     */
    public UniversalView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * Initialize VIS.X Ad with required params like Ad Unit ID, Domain and Size
     */
    public void init() {

        /**
         * Add custom made universal_view layout to View wrapper
         */
        inflate(context, R.layout.universal_view, this);

        /**
         * Must be run on React-Native UI thread for platform sync
         */
        ((ThemedReactContext) context).getCurrentActivity().runOnUiThread(() -> {
            visxAdManager = new VisxAdManager.Builder()
                    .visxAdUnitID(UNIVERSAL_AD_UNIT)
                    .adSize(AdSize.MEDIUM_RECTANGLE_300x250)
                    .appDomain(DOMAIN)
                    .context(((ThemedReactContext) context).getCurrentActivity())
                    .callback(new ActionTracker() {

                        /**
                         * VIS.X Ad callbacks
                         */
                        @Override
                        public void onAdRequestStarted(VisxAdManager visxAdManager) {
                            Log.d(TAG, ACTION_TRACKER + " onAdRequestStarted");
                        }

                        @Override
                        public void onAdResponseReceived(VisxAdManager visxAdManager, String s) {
                            Log.d(TAG, ACTION_TRACKER + " onAdResponseReceived: " + s);

                            /**
                             * Init Display VIS.X Ad
                             */
                            displayAd(visxAdManager);
                        }

                        @Override
                        public void onAdLoadingStarted(VisxAdManager visxAdManager) {
                            Log.d(TAG, ACTION_TRACKER + " onAdLoadingStarted");
                        }

                        @Override
                        public void onAdLoadingFinished(VisxAdManager visxAdManager, String s) {
                            Log.d(TAG, ACTION_TRACKER + " onAdLoadingFinished");

                            /**
                             * Display VIS.X Ad
                             */
                            displayAd(visxAdManager);
                        }

                        @Override
                        public void onAdLoadingFailed(VisxAdManager visxAdManager, String s, boolean b) {
                            Log.d(TAG, ACTION_TRACKER + " onAdLoadingFailed reason: " + s);
                        }

                        @Override
                        public void onAdSizeChanged(int width, int height) {
                            Log.d(TAG, ACTION_TRACKER
                                    + " onAdSizeChanged width: " + width
                                    + " height: " + height);

                            /**
                             * Prepare size values for React-Native App adContainer resizing
                             */
                            final WritableMap params = Arguments.createMap();
                            params.putString(KEY_WIDTH, String.valueOf(width));
                            params.putString(KEY_HEIGHT, String.valueOf(height));
                            sendEvent((ReactContext) context, params);
                        }

                        @Override
                        public void onAdClicked() {
                            Log.d(TAG, ACTION_TRACKER + " onAdClicked");
                        }

                        @Override
                        public void onAdLeftApplication() {
                            Log.d(TAG, ACTION_TRACKER + " onAdLeftApplication");
                        }
                    }).build();

            /**
             * VIS.X Ad tracker callbacks
             */
            visxAdManager.setAdActionTracker(new AdActionTracker() {
                @Override
                public void onInterstitialWillBeClosed() {
                    Log.d(TAG, AD_ACTION_TRACKER + " onInterstitialWillBeClosed");
                }

                @Override
                public void onInterstitialClosed() {
                    Log.d(TAG, AD_ACTION_TRACKER + " onInterstitialClosed");
                }

                @Override
                public void onLandingPageOpened(boolean b) {
                    Log.d(TAG, AD_ACTION_TRACKER + " onLandingPageOpened: " + b);
                }

                @Override
                public void onLandingPageClosed() {
                    Log.d(TAG, AD_ACTION_TRACKER + " onLandingPageClosed");
                }

                @Override
                public void onAdResized(int i, int i1, int i2, int i3, EnhancedMraidProperties.CloseButtonPosition closeButtonPosition) {
                    Log.d(TAG, AD_ACTION_TRACKER + " onAdResized");
                }

                @Override
                public void onResizedAdClosed() {
                    Log.d(TAG, AD_ACTION_TRACKER + " onResizedAdClosed");
                }
            });
        });
    }

    /**
     * Send event with size updates to React-Native App adContainer
     */
    private void sendEvent(ReactContext reactContext, @Nullable WritableMap params) {
        Log.i(TAG, "Send Event from Android to ReactNative " + EVENT_NAME);
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(EVENT_NAME, params);
    }

    /**
     * Display VIS.X Ad on the main UI thread
     */
    private void displayAd(VisxAdManager visxAdManager) {
        ((ThemedReactContext) context).getCurrentActivity().runOnUiThread(() -> {
            Log.i(TAG, "SDK Version: " + visxAdManager.getSdKVersion());

            /**
             * Init RelativeLayout View wrapper for VIS.X Ad to be injected in
             */
            RelativeLayout inlineContainer = findViewById(R.id.inlineContainer);

            /**
             * Remove visxAdContainer if has a parent View
             */
            if (visxAdManager.getAdContainer().getParent() != null) {
                ((ViewGroup) visxAdManager.getAdContainer().getParent()).removeView(visxAdManager.getAdContainer());
            }

            /**
             * Inject VIS.X Ad to View wrapper
             */
            inlineContainer.addView(visxAdManager.getAdContainer());
            inlineContainer.invalidate();
        });
    }

    /**
     * Dismiss VIS.X Instance
     */
    @Override
    protected void onDetachedFromWindow() {
        if (visxAdManager != null) {
            visxAdManager.stop();
        }
        super.onDetachedFromWindow();
    }
}
