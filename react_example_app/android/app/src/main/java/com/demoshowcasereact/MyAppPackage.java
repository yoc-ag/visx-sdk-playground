package com.demoshowcasereact;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAppPackage implements ReactPackage {

    /**
     * Register View managers for React-Native App
     * VIS.X Inline Banner/Video and Universal Ad
     * @return List of View Managers
     */
    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new BannerReactViewManager(),
                new UniversalReactiveViewManager()
        );
    }

    /**
     * Register Native Module for React-Native App communication
     * VIS.X Mystery (Interstitial) Ad
     * @return List of Native Modules
     */
    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();

        modules.add(new VisxInterstitialModule(reactContext));

        return modules;
    }

}
