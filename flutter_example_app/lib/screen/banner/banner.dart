import 'dart:io';

import 'package:demo_showcase_flutter/common/colors.dart';
import 'package:demo_showcase_flutter/common/constants.dart';
import 'package:demo_showcase_flutter/common/dimen.dart';
import 'package:demo_showcase_flutter/common/strings.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';

class Banner extends StatefulWidget {
  BannerState createState() => BannerState();
}

class BannerState extends State<Banner> {
  // Pass parameters to the platform side.
  final Map<String, dynamic> creationParams = <String, dynamic>{};

  Widget _getPlatformAd() {
    creationParams[Constants.AD_TYPE] = Constants.BANNER;

    if (Platform.isAndroid) {
      creationParams[Constants.AD_UNIT_ID] = Constants.AD_UNIT_BANNER_ID;
      print(Platform.operatingSystem);
      // Android-specific code
      return PlatformViewLink(
        viewType: Constants.ANDROID_BRIDGE,
        surfaceFactory:
            (BuildContext context, PlatformViewController controller) {
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
      // iOS-specific code
      print(Platform.operatingSystem);

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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(Strings.bannerTitle),
        backgroundColor: AppColors.colorPrimaryDark,
        actions: [
          Padding(
            padding: const EdgeInsets.all(10.0),
            child: Image(
              image: AssetImage('assets/images/yoc_logo.png'),
            ),
          ),
        ],
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            Flexible(
              child: ListView(
                physics: const AlwaysScrollableScrollPhysics(),
                scrollDirection: Axis.vertical,
                shrinkWrap: true,
                children: [
                  Image(
                    image: AssetImage('assets/images/teaser_img_article.png'),
                  ),
                  Padding(
                    padding:
                        const EdgeInsets.all(Dimen.activityHorizontalMargin),
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
                      style: const TextStyle(
                          fontSize: 14, color: AppColors.blackText),
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Container(
                          width: 320, height: 250, child: _getPlatformAd()),
                    ],
                  ),
                  Padding(
                    padding: const EdgeInsets.only(
                        left: Dimen.activityHorizontalMargin,
                        right: Dimen.activityHorizontalMargin),
                    child: Text(
                      Strings.dummyText,
                      style: const TextStyle(
                          fontSize: 14, color: AppColors.blackText),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
