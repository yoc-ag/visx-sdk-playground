import { requireNativeComponent, ViewPropTypes } from "react-native";
module.exports = Platform.select({
    ios: () => requireNativeComponent("VisxBannerView", null),
    android: () => requireNativeComponent("BannerReactViewManager", null)
  })();
  