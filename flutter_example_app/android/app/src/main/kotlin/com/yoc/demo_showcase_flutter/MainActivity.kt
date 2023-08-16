package com.yoc.demo_showcase_flutter

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.yoc.demo_showcase_flutter.Constants.ANDROID_BRIDGE
import com.yoc.visx.sdk.VisxSDK
import com.yoc.visx.sdk.adview.VisxAd
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import kotlin.properties.Delegates


/**
 * This is main and only input output point for cmmunicating between Flutter and Android Native
 * platforms.
 *
 * Need to implement EventChannel.StreamHandler interface for opening EventChannel for data Streams
 */
class MainActivity : FlutterActivity(), EventChannel.StreamHandler {

    /**
     * Observed y position of adContainer from Flutter platform
     * This value changes dynamically
     */
    val viewDataObserversY = mutableListOf<(String) -> Unit>()

    var y: String by Delegates.observable("") { _, _, newValue ->
        viewDataObserversY.forEach { it(newValue) }
    }

    /**
     * For setting up streams of data from Native Android to Flutter platform
     * via EventChannel
     */
    private var eventSink: EventChannel.EventSink? = null

    companion object {
        const val TAG = "MainActivity--->"

        // Flutter channels (Method and Event)
        const val CHANNEL_METHOD = "visx.sdk"
        const val CHANNEL_EVENT = "event.channel.ad.size.change"

        // Channel name for view values
        const val VIEW_VALUES_CHANNEL_TAG = "raw.values.android"

        // Values for Interstitial AD
        const val INTERSTITIAL_CHANNEL_TAG = "interstitial.android"
        const val YOC_INTERSTITIAL_VIS_X_AD_UNIT_ID: String = "912263"
        const val APP_DOMAIN = "yoc.com"
    }

    /**
     * Setting up Flutter and Android Native communication
     */
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        VisxSDK.initialize(applicationContext, Constants.SITE_ID)
        setEventChannel(flutterEngine)
        setMethodChannel(flutterEngine)
        setFlutterNativeView(flutterEngine)
    }

    /**
     * Event Channel for transferring ad size dynamic data from Native Android to Flutter platform
     */
    private fun setEventChannel(flutterEngine: FlutterEngine) {
        val event = EventChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL_EVENT)
        event.setStreamHandler(this)
    }

    /**
     * Method Channel for receiving interactions from Flutter platform
     *
     * Only one method channel is needed for multiple interactions
     * Method Channel can also return some value to Flutter platform
     *
     * Receiving view values (exposureChange, y, x, anchorViewSize)
     * Receiving call for displaying Interstitial AD
     * Receiving other interactions
     */
    private fun setMethodChannel(flutterEngine: FlutterEngine) {
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL_METHOD).setMethodCallHandler { call, result ->

            /**
             * Note: this method is invoked on the main thread.
             * */
            when (call.method) {

                /**
                 * Interstitial is called from Flutter platform
                 */
                INTERSTITIAL_CHANNEL_TAG -> {
                    displayInterstitial()
                }

                /**
                 * View values for adContainer are transferred from Flutter platform
                 */
                VIEW_VALUES_CHANNEL_TAG -> {
                    setY(call)
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    /**
     * Registering Native Android View (VisxAd) to be displayed in Flutter platform
     */
    private fun setFlutterNativeView(flutterEngine: FlutterEngine) {
        flutterEngine
                .platformViewsController
                .registry
                .registerViewFactory(ANDROID_BRIDGE, NativeViewFactory(this))
    }

    /**
     * Setting and displaying Interstitial Creative
     */
    private fun displayInterstitial() {
        VisxAd(this).displayInterstitial(YOC_INTERSTITIAL_VIS_X_AD_UNIT_ID)
    }

    /**
     * Setting view values for y position received from Flutter platform
     *
     * This y value is received via Method Channel tracking Scroll events on Flutter side
     * and populating y variable that is observed by VisxAd.
     *
     * This y value is mandatory for VIS.X Universal Ad (Understitial effect)
     */
    private fun setY(call: MethodCall) {
        this.y = call.argument<String>("y") ?: "0.0"
    }

    /**
     * Register EventChannel Stream listener in Flutter platform
     *
     * Listens to adSize changes width and height.
     * Values needed for Flutter platform adContainer to resize properly.
     */
    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        Log.i(TAG, "onListen: arguments: $arguments events: $events")
        eventSink = events
    }

    /**
     * Remove EventChannel Stream listener
     */
    override fun onCancel(arguments: Any?) {
        Log.i(TAG, "onCancel: arguments: $arguments")
        eventSink = null
    }

    /**
     * Sending data to Flutter platform for adContainer size values
     */
    fun updateAdContainer(width: Int, height: Int) {
        runOnUiThread {
            Log.i(TAG, "updateAdContainer: width:$width, height:$height")
            val values = listOf(width.toDouble(), height.toDouble())
            eventSink!!.success(values)
        }
    }
}
