import { requireNativeComponent, ViewPropTypes, Platform } from "react-native";
//
module.exports = Platform.select({
    ios: () => requireNativeComponent("VisxUniversalView", null),
    android: () => requireNativeComponent("UniversalReactiveViewManager", null)
  })();
  