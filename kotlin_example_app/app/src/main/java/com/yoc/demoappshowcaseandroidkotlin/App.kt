package com.yoc.demoappshowcaseandroidkotlin

import android.app.Application
import com.yoc.visx.sdk.VisxSDK
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initTimber()
        VisxSDK.initialize(applicationContext, "yoc_site_id")
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}