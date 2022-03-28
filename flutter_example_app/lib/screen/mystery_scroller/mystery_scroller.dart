import 'package:demo_showcase_flutter/common/colors.dart';
import 'package:demo_showcase_flutter/common/dimen.dart';
import 'package:demo_showcase_flutter/common/strings.dart';
import 'package:flutter/material.dart';

class MysteryScroller extends StatefulWidget {
  MysteryScrollerState createState() => MysteryScrollerState();
}

class MysteryScrollerState extends State<MysteryScroller> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(Strings.mysteryScrollerTitle),
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
                    padding: const EdgeInsets.all(30),
                    child: Text(
                      "TODO: Implement MysteryScroller Ad Here",
                      style: const TextStyle(
                          fontSize: Dimen.textSizeXXLarge,
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
