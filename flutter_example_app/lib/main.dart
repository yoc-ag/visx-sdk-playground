import 'package:demo_showcase_flutter/screen/banner/banner.dart' as route;
import 'package:demo_showcase_flutter/screen/home/home_screen.dart';
import 'package:demo_showcase_flutter/screen/interstitial/interstitial.dart' as route;
import 'package:demo_showcase_flutter/screen/mystery_scroller/mystery_scroller.dart' as route;
import 'package:demo_showcase_flutter/screen/universal//universal.dart' as route;
import 'package:flutter/material.dart';

import 'common/routes.dart';

void main() {
  runApp(
    MaterialApp(
      home: HomeScreen(),
      routes: <String, WidgetBuilder>{
        Routes.banner: (BuildContext context) => route.Banner(),
        Routes.interstitial: (BuildContext context) => route.Interstitial(),
        Routes.universal: (BuildContext context) => route.Universal(),
        Routes.mysteryScroller: (BuildContext context) => route.MysteryScroller(),
      },
    ),
  );
}
