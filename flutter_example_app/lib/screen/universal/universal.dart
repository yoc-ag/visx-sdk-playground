import 'dart:async';
import 'dart:io';

import 'package:demo_showcase_flutter/common/colors.dart';
import 'package:demo_showcase_flutter/common/constants.dart';
import 'package:demo_showcase_flutter/common/dimen.dart';
import 'package:demo_showcase_flutter/common/listener.dart';
import 'package:demo_showcase_flutter/common/strings.dart';
import 'package:demo_showcase_flutter/size_provider_widget.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:visibility_detector/visibility_detector.dart';

/// Widget for displaying VIS.X Universal AD
class Universal extends StatefulWidget {
  _UniversalState createState() => _UniversalState();
}

class _UniversalState extends State<Universal> {

  /// Platform Method Channel definition
  static const platform = const MethodChannel('visx.sdk');

  /// Method channel keys for calling native method/functions
  static const viewValuesDataAndroid = 'raw.values.android';
  static const viewValuesDataIos = 'raw.values.ios';

  /// Stream listener for ad size changes from native side
  StreamSubscription _streamSubscription;

  /// List of values about the size of the creative
  List<double> adSize;

  /// Height of the Flutter adContainer
  ///
  /// This values is dynamically changed and follows adSize.height
  /// changes received from native platform
  var height = 0.0;

  /// Visible part of the screen where the content can be shown
  /// and resize accordingly
  var bodyHeight = 0.0;

  /// Map for storing parameters for sending to native platform side.
  ///
  /// For passing creative [TYPE] and [bodyHeight] value,
  /// and other values if needed (auid, app domain, ect.)
  final Map<String, dynamic> creationParams = <String, dynamic>{};

  @override
  initState() {
    super.initState();
    adSize = <double>[0, 0];

    /// Receive data from native side about adSize changes
    ///
    /// Subscribe Event Channel [listener.dart][EventChannelData]
    _streamSubscription = eventData.listen((event) {
      if (mounted) {
        setState(() {
          adSize = <double>[event.x, event.y];

          /// Setting adContainer widget height dynamically
          height = event.y;
        });
      }
    });
  }

  /// Platform specification for receiving and displaying
  /// native View Widget (Android and iOS)
  Widget _getPlatformAd() {

    /// Setting ad TYPE
    creationParams[Constants.AD_TYPE] = Constants.UNIVERSAL;

    /// Setting bodyHeight
    creationParams[Constants.MAX_SIZE_HEIGHT] = bodyHeight.toInt();

    /// Platform check
    if (Platform.isAndroid) {
      creationParams[Constants.AD_UNIT_ID] = Constants.AD_UNIT_UNIVERSAL_ID;
      /// Android-specific code
      return PlatformViewLink(
        viewType: Constants.ANDROID_BRIDGE,
        surfaceFactory: (BuildContext context, PlatformViewController controller) {
          return AndroidViewSurface(
            controller: controller,
            gestureRecognizers: const <Factory<OneSequenceGestureRecognizer>>{},
            hitTestBehavior: PlatformViewHitTestBehavior.opaque,
          );
        },
        onCreatePlatformView: (PlatformViewCreationParams params) {
          return PlatformViewsService.initSurfaceAndroidView(
            id: params.id,
            viewType: Constants.ANDROID_BRIDGE,
            layoutDirection: TextDirection.ltr,
            creationParams: creationParams,
            creationParamsCodec: StandardMessageCodec(),
          )
            ..addOnPlatformViewCreatedListener(params.onPlatformViewCreated)
            ..create();
        },
      );
    } else if (Platform.isIOS) {

      /// iOS-specific code
      return UiKitView(
        viewType: Constants.IOS_BRIDGE,
        layoutDirection: TextDirection.ltr,
        creationParams: creationParams,
        creationParamsCodec: const StandardMessageCodec(),
      );
    } else {
      return Text(
        "Unsupported platform!",
        style: TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: Dimen.textSizeXXLarge,
            color: AppColors.turquoise),
      );
    }
  }

  /// Raw view values for calculating position of Flutter adContainer
  ///
  /// [_y]              - y position of adContainer
  double _y = 0;

  /// Key definition for Widget as ID
  final containerKey = GlobalKey();

  /// Initialize bridge between Flutter and Native Android/iOS platform
  ///
  /// Method Channel
  Future<void> _getRawData() async {

    /// Data to be send to the native side
    var data = {
      "y": _y.toString(),
    };

    if (Platform.isAndroid) {
      try {
        final int result = await platform.invokeMethod(viewValuesDataAndroid, data);
      } on PlatformException catch (e) {
        print("Android Failed to load: $e");
      }
    } else {
      print("Failed to load, unsupported platform");
    }
  }

  @override
  Widget build(BuildContext context) {

    _getRawData();

    AppBar appBar = AppBar(
      title: Text(Strings.universalTitle),
      backgroundColor: AppColors.colorPrimaryDark,
      actions: [
        Padding(
          padding: const EdgeInsets.all(10.0),
          child: Image(
            image: AssetImage('assets/images/yoc_logo.png'),
          ),
        ),
      ],
    );

    var paddingTop = MediaQuery.of(context).padding.top;

    return Scaffold(
      appBar: appBar,
      body: Builder(builder: (context) {

        /// Initialize visible part of the Scaffold
        /// Top and bottom offset should be taken in
        /// to consideration (appBar, bottomNavBar, overlay Widget, etc.)
        bodyHeight = MediaQuery.of(context).size.height - Scaffold.of(context).appBarMaxHeight;
        return Stack(
          children: <Widget>[
            Center(
              child: Column(
                children: <Widget>[
                  Flexible(

                      /// Scroll Notification Listener
                      ///
                      /// * Mandatory for VIS.X SDK Understitial effect *
                      child: NotificationListener<ScrollNotification>(
                        onNotification: (notification) {
                          setState(() {

                            /// Listening for y and x position for Flutter adContainer
                            if (containerKey.globalPaintBounds != null) {

                              /// Top left position of the adContained in parent Widget (_anchorView)
                              /// y axis of the adContained Widget
                              ///
                              /// Top and offset should be taken in
                              /// to consideration (appBar, statusBar, etc.)
                              ///
                              /// _y position 0 should be the top left position of adContainer in the
                              /// visible part of the parent Widget
                              _y = containerKey.globalPaintBounds.top -
                                  appBar.preferredSize.height -
                                  paddingTop;
                            }
                          });
                          return true;
                        },

                        /// Use this Widget to have VisxAd active
                        ///
                        /// * Important: Do not use ScrollView, it recreates the native
                        /// View once off visible part of the screen, thus making another
                        /// ad call *
                        child: SingleChildScrollView(
                          physics: const ScrollPhysics(),
                          scrollDirection: Axis.vertical,
                          child: Column(
                            children: <Widget>[
                              Image(
                                image: AssetImage('assets/images/teaser_img_article.png'),
                              ),
                              Padding(
                                padding: const EdgeInsets.all(10.0),
                                child:
                                    Text('View Size\nwidth:${adSize[0]}\nheight: ${adSize[1]}'),
                              ),
                              Padding(
                                padding: const EdgeInsets.all(Dimen.activityHorizontalMargin),
                                child: Text(
                                  Strings.headline.toUpperCase(),
                                  style: const TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 26,
                                      fontFamily: 'Rift',
                                      color: AppColors.blackText),
                                ),
                              ),
                              Padding(
                                padding: const EdgeInsets.only(
                                    left: Dimen.activityHorizontalMargin,
                                    right: Dimen.activityHorizontalMargin),
                                child: Text(
                                  Strings.dummyText,
                                  style: const TextStyle(fontSize: 14, color: AppColors.blackText),
                                ),
                              ),
                              Row(

                                /// ID key for following this Widget
                                key: containerKey,
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: <Widget>[

                                        /// Flutter adContainer for VIS.X creative
                                    Container(
                                        width: MediaQuery.of(context).size.width,

                                        /// Dynamic value following ad resizing in height
                                        height: height,

                                        /// Get platform Native View Android/iOS
                                        child: _getPlatformAd()),
                                ],
                              ),
                              Padding(
                                padding: const EdgeInsets.only(
                                    left: Dimen.activityHorizontalMargin,
                                    right: Dimen.activityHorizontalMargin),
                                child: Text(
                                  Strings.dummyText,
                                  style: const TextStyle(fontSize: 14, color: AppColors.blackText),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                ],
              ),
            ),
          ],
        );
      }),
    );
  }
}

/// Extension function for [GlobalKey]
///
/// Returns size values for Widget
extension GlobalKeyExtension on GlobalKey {
  Rect get globalPaintBounds {
    final renderObject = currentContext?.findRenderObject();
    var translation = renderObject?.getTransformTo(null)?.getTranslation();
    if (translation != null && renderObject.paintBounds != null) {
      return renderObject.paintBounds.shift(Offset(translation.x, translation.y));
    } else {
      return null;
    }
  }
}
