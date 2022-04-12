package com.yoc.demoappshowcaseandroidkotlin

import android.app.Application
import androidx.preference.PreferenceManager
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
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this) ?: return
        with (sharedPref.edit()) {
            putString("IABTCF_TCString", "CO7_Ji2O7_Ji2NSAAAENAwCAAAAAAAAAAAAAF5wCAAWgAyACIAIEATQA8gCdgLzAAAAA.IGLtV_T9fb2vj-_Z99_tkeYwf95y3p-wzhheMs-8NyZeH_B4Wv2MyvBX4JiQKGRgksjLBAQdtHGlcTQgBwIlViTLMYk2MjzNKJrJEilsbO2dYGD9Pn8HT3ZCY70-vv__7v3ff_3g");
            putInt("IABTCF_gdprApplies", 1);
            apply()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}