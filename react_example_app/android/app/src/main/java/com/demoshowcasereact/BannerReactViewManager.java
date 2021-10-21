package com.demoshowcasereact;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

/**
 * Setting up VIS.X Inline Banner/Video Ad Module
 */
public class BannerReactViewManager extends SimpleViewManager<BannerView> {
    public static final String REACT_CLASS = "BannerReactViewManager";

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected BannerView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new BannerView(reactContext); //If your customview has more constructor parameters pass it from here.
    }
}
