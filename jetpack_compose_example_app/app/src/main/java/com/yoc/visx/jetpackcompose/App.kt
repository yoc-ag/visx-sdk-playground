package com.yoc.visx.jetpackcompose

import android.app.Application
import com.yoc.visx.jetpackcompose.common.Constants
import com.yoc.visx.sdk.VisxSDK

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        VisxSDK.initialize(applicationContext, Constants.SITE_ID)
    }
}