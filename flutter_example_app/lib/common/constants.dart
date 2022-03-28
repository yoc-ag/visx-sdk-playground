class Constants {
  /// Used in the platform side to register and display the view.
  /// Need to be same on Flutter and Android/iOS side.
  static const String ANDROID_BRIDGE = "android_view_type";
  static const String IOS_BRIDGE = "ios_view_type";

  /// Key for setting the ad Type
  static const String AD_TYPE = "ad_type";

  /// Value for ad TYPE
  static const String BANNER = "banner";
  static const String UNIVERSAL = "universal";

  /// Key for setting bodyHeight value
  static const String MAX_SIZE_HEIGHT = "max_size_height";

  /// Key for setting adUnitId value
  static const String AD_UNIT_ID = "ad_unit_id";

  /// Ad unit ID`s
  static const String AD_UNIT_BANNER_ID = "912261";
  static const String AD_UNIT_UNIVERSAL_ID = "912268";
}
