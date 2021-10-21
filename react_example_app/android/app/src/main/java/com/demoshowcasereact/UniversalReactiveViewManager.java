package com.demoshowcasereact;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

/**
 * Setting up VIS.X Universal Ad Module
 */
public class UniversalReactiveViewManager  extends SimpleViewManager<UniversalView> {
    public static final String REACT_CLASS = "UniversalReactiveViewManager";

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected UniversalView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new UniversalView(reactContext); //If your customview has more constructor parameters pass it from here.
    }
}
