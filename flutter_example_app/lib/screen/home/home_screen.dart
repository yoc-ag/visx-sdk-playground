import 'package:demo_showcase_flutter/common/colors.dart';
import 'package:demo_showcase_flutter/common/dimen.dart';
import 'package:demo_showcase_flutter/common/routes.dart';
import 'package:demo_showcase_flutter/common/strings.dart';
import 'package:flutter/material.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.colorPrimaryDark,
      appBar: AppBar(
        title: Text(Strings.appName),
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
        child: Padding(
          padding: const EdgeInsets.only(
              left: Dimen.activityHorizontalMargin,
              right: Dimen.activityHorizontalMargin),
          child: Column(
            children: <Widget>[
              Row(
                mainAxisSize: MainAxisSize.max,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Padding(
                    padding: const EdgeInsets.only(top: 30),
                    child: ElevatedButton(
                      style: ButtonStyle(
                        fixedSize: MaterialStateProperty.all<Size>(
                            Size(300, double.minPositive)),
                        backgroundColor: MaterialStateProperty.all<Color>(
                            AppColors.colorPrimaryLight),
                      ),
                      child: Text(Strings.bannerShowcase),
                      onPressed: () {
                        Navigator.of(context).pushNamed(Routes.banner);
                      },
                    ),
                  ),
                ],
              ),
              Padding(
                padding: const EdgeInsets.only(top: 10),
                child: ElevatedButton(
                  style: ButtonStyle(
                    fixedSize: MaterialStateProperty.all<Size>(
                        Size(300, double.minPositive)),
                    backgroundColor: MaterialStateProperty.all<Color>(
                        AppColors.colorPrimaryLight),
                  ),
                  child: Text(Strings.interstitialShowcase),
                  onPressed: () {
                    Navigator.of(context).pushNamed(Routes.interstitial);
                  },
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 10),
                child: ElevatedButton(
                  style: ButtonStyle(
                    fixedSize: MaterialStateProperty.all<Size>(
                        Size(300, double.minPositive)),
                    backgroundColor: MaterialStateProperty.all<Color>(
                        AppColors.colorPrimaryLight),
                  ),
                  child: Text(Strings.universalShowcase),
                  onPressed: () {
                    Navigator.of(context).pushNamed(Routes.universal);
                  },
                ),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 10),
                child: ElevatedButton(
                  style: ButtonStyle(
                    fixedSize: MaterialStateProperty.all<Size>(
                        Size(300, double.minPositive)),
                    backgroundColor: MaterialStateProperty.all<Color>(
                        AppColors.colorPrimaryLight),
                  ),
                  child: Text(Strings.mysteryScrollerShowcase),
                  onPressed: () {
                    Navigator.of(context).pushNamed(Routes.mysteryScroller);
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
