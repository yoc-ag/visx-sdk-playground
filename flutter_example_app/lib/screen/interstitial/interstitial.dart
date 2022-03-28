import 'dart:io';

import 'package:demo_showcase_flutter/common/assets.dart';
import 'package:demo_showcase_flutter/common/colors.dart';
import 'package:demo_showcase_flutter/common/dimen.dart';
import 'package:demo_showcase_flutter/common/strings.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';

class Interstitial extends StatefulWidget {
  _InterstitialState createState() => _InterstitialState();
}

class _InterstitialState extends State<Interstitial> {
  static const platform = const MethodChannel('visx.sdk');
  static const displayInterstitialActionAndroid = 'interstitial.android';
  static const displayInterstitialActionIos = 'interstitial.ios';

  Future<void> _getInterstitial() async {
    if (Platform.isAndroid) {
      try {
        await platform.invokeMethod(displayInterstitialActionAndroid);
      } on PlatformException catch (e) {
        _showToast("Interstitial Android failed to load: ${e.message}");
      }
    } else if (Platform.isIOS) {
      try {
        await platform.invokeMethod(displayInterstitialActionIos);
      } on PlatformException catch (e) {
        _showToast("Interstitial iOS failed to load: ${e.message}");
      }
    } else {
      _showToast("Interstitial failed to load: Unsupported platform");
    }
  }

  _showToast(String message) {
    Fluttertoast.showToast(
        msg: message,
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.CENTER,
        timeInSecForIosWeb: 1,
        backgroundColor: Colors.red,
        textColor: Colors.white,
        fontSize: 16.0);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(Strings.interstitialTitle),
        backgroundColor: AppColors.colorPrimaryDark,
        actions: [
          Padding(
            padding: const EdgeInsets.all(10.0),
            child: Image(
              image: AssetImage(Assets.logo),
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
                    image: AssetImage(Assets.header),
                  ),
                  Padding(
                    padding:
                        const EdgeInsets.all(Dimen.activityHorizontalMargin),
                    child: Text(
                      Strings.headline.toUpperCase(),
                      style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: Dimen.textSizeXXLarge,
                          fontFamily: Assets.fontFamilyRift,
                          color: AppColors.blackText),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(
                        left: Dimen.activityHorizontalMargin,
                        right: Dimen.activityHorizontalMargin),
                    child: ElevatedButton(
                      child: Text(Strings.displayInterstitial),
                      onPressed: _getInterstitial,
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
